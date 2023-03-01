package com.tinyspace.tinytask.counter

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tinyspace.common.BaseUiState
import com.tinyspace.common.BaseViewModel
import com.tinyspace.common.BaseViewModelState
import com.tinyspace.common.formatSeconds
import com.tinyspace.shared.core.AppCountDownTimer
import com.tinyspace.shared.domain.GetTaskUseCase
import com.tinyspace.shared.domain.UpdateTaskUseCase
import com.tinyspace.shared.domain.model.Tag
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import com.tinyspace.shared.domain.model.Task as DomainTask


private val INTERVAL = 1.toDuration(DurationUnit.SECONDS)
private val ZERO = 0.toDuration(DurationUnit.SECONDS)
//val TOTAL = 1.toDuration(DurationUnit.MINUTES)
private val TOTAL = 10.toDuration(DurationUnit.SECONDS)

const val TAG = "CounterViewModel"

const val EmptyMessage = ""

class CounterViewModel(
    private val taskId: String,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
) : BaseViewModel<CounterEvent, CounterUiState, CounterVMState>(), KoinComponent {

    override val initialState: CounterVMState = CounterVMState(
        stop = true, current = ZERO, total = ZERO, isLoading = true,
        message = EmptyMessage
    )

    override val modelState = MutableStateFlow(initialState)

    override val uiState = modelState.map(CounterVMState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            modelState.value.toUiState()
        )

    private var counterJob: Job = Job().apply { complete() }
    private lateinit var countDownFlow: Flow<Duration>


    init {
        modelState.update {
            it.copy(taskId = taskId)
        }

        viewModelScope.launch {
            try {
                val task = getTaskUseCase(taskId)
                modelState.update {
                    it.copy(
                        total = task.duration,
                        title = task.title,
                        current = task.duration,
                        tags = task.tags
                    )
                }
            } catch (ex: Exception) {
                modelState.update {
                    it.copy(
                        message = ex.toString(),
                    )
                }
            } finally {
                modelState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }


    private fun start() {
        countDownFlow = createCountDownJob(modelState.value.total)
        counterJob = countDownFlow.launchIn(viewModelScope)
    }

    private fun stop() {
        counterJob.cancel("Counter Stop")
    }

    private fun resume() {
        countDownFlow = createCountDownJob(modelState.value.current)
        counterJob = countDownFlow.launchIn(viewModelScope)
    }

    private fun createCountDownJob(total: Duration) : Flow<Duration> {
      return AppCountDownTimer.create(
            total,
            INTERVAL
        ).onStart {
          modelState.update { state ->
              state.copy(stop = false)
          }
        }.onCompletion {
            if(it != null) {
                modelState.update { state ->
                    state.copy(stop = true)
                }
            } else {
                Log.d(TAG, "Counter finished")
            }
      }.onEach {
          modelState.update { state ->
              state.copy(current = it)
          }
      }.catch {
          if (it is CancellationException) {
              Log.d(TAG, "Counter stopped")
          }
      }
    }

    private fun restart() {
        TODO("I haven't think how I will implement this function")
    }

    private fun finish() {
        viewModelScope.launch {
            updateTaskUseCase(modelState.value.toDomainModel())
            delay(100)
            modelState.update {
                it.copy(isNavigateBack = true)
            }
        }
    }

    override fun onEvent(event: CounterEvent) = when (event) {
        CounterEvent.Finish -> finish()
        CounterEvent.Start -> start()
        CounterEvent.Resume -> resume()
        CounterEvent.Stop -> stop()
        CounterEvent.Restart -> {
            modelState.update {
                it.copy(total = TOTAL, current = TOTAL, stop = false)
            }
        }
    }
}

data class CounterVMState(
    val total: Duration = TOTAL,
    val current: Duration,
    val stop: Boolean,
    val taskId: String = "",
    val title: String = "",
    val isNavigateBack: Boolean = false,
    val isLoading: Boolean,
    val message: String,
    val tags: List<Tag> = emptyList()
) : BaseViewModelState<CounterUiState> {

    private val initial: Boolean get() = total == ZERO && current == ZERO
    private val progress: Float get() = (current / total).toFloat()
    private val finish: Boolean get() = total > ZERO && current <= ZERO
    private val error: Boolean get() = message.isNotEmpty()

    override fun toUiState(): CounterUiState = when {
        isNavigateBack -> CounterUiState.NavigateBack()
        error -> CounterUiState.Error(message = message)
        finish -> CounterUiState.Finish(current.durationToLong())
        stop -> CounterUiState.Pause(current.durationToLong(), progress)
        initial -> CounterUiState.Initial(total.durationToLong(), progress)
        else -> CounterUiState.Counting(
            current.durationToLong(),
            progress
        )
    }.apply {
        tags = this@CounterVMState.tags.map { it.name }
        title = this@CounterVMState.title
    }

    private fun Duration.durationToLong(): Int {
        return ceil(this.toDouble(DurationUnit.SECONDS)).toInt()
    }

    fun toDomainModel(): DomainTask {
        return DomainTask.instanceForUpdate(taskId, finish)
    }
}

sealed class CounterUiState(
    val stop: Boolean = false,
    val message: String = "",
    var title: String = "",
    var tags: List<String> = emptyList()
) : BaseUiState {
    abstract val counter: Int
    abstract val progress: Float
    val counting: Boolean get() = progress > 0 && progress < 1
    val finished: Boolean get() = counter <= 0
    val timer: String get() = counter.formatSeconds()
    val initial get() = progress >= 1f

    data class Counting(override val counter: Int, override val progress: Float) : CounterUiState()

    data class Initial(override val counter: Int, override val progress: Float) :
        CounterUiState(stop = true)

    data class Pause(override val counter: Int, override val progress: Float) :
        CounterUiState(stop = true)

    data class Finish(override val counter: Int, override val progress: Float = 0f) :
        CounterUiState()

    data class NavigateBack(
        override val counter: Int = 0, override val progress: Float = 0f
    ) : CounterUiState()

    class Error(
        override val counter: Int = 0, override val progress: Float = 0f,
        message: String
    ) : CounterUiState(
        message = message
    )
}


