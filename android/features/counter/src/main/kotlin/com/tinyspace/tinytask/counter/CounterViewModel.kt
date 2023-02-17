package com.tinyspace.tinytask.counter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyspace.common.formatSeconds
import com.tinyspace.shared.core.AppCountDownTimer
import com.tinyspace.shared.domain.UpdateTaskUseCase
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

private val initialState = CounterViewModelState(stop = true, current = TOTAL)

class CounterViewModel(
    private val taskId: String,
    private val updateTaskUseCase: UpdateTaskUseCase,
//    private val getTaskUseCase: GetTaskUseCase,
) : ViewModel(), KoinComponent {

    private val modelState = MutableStateFlow(initialState)

    internal val uiState = modelState.map(CounterViewModelState::toUiState)
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

    private fun finish() {
        viewModelScope.launch {
            updateTaskUseCase(modelState.value.toDomainModel())
            delay(100)
            modelState.update {
                it.copy(isNavigateBack = true)
            }
        }
    }

    fun onEvent(event: CounterEvent) = when (event) {
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

private data class CounterViewModelState(
    val total: Duration = TOTAL,
    val current: Duration,
    val stop: Boolean,
    val taskId: String = "",
    val title: String = "",
    val isNavigateBack: Boolean = false
) {

    private val initial: Boolean get() = total == current
    private val progress : Float get() = (current / total).toFloat()
    private val finish: Boolean get() = current <= ZERO

    fun toUiState(): CounterUiState = when {
        isNavigateBack -> CounterUiState.NavigateBack()
        finish -> CounterUiState.Finish(ceil(current.toDouble(DurationUnit.SECONDS)).toInt())
        stop -> CounterUiState.Pause(ceil(current.toDouble(DurationUnit.SECONDS)).toInt(), progress)
        initial -> CounterUiState.Initial(total.toInt(DurationUnit.SECONDS), progress)
        else -> CounterUiState.Counting(
            ceil(current.toDouble(DurationUnit.SECONDS)).toInt(),
            progress
        )
    }

    fun toDomainModel(): DomainTask {
        return DomainTask.instanceForUpdate(taskId, finish)
    }
}

internal sealed class CounterUiState(
    val stop: Boolean = false,
) {
    abstract val counter: Int
    abstract val progress: Float
    val finish: Boolean get() = counter <= 0
    val timer: String get() = counter.formatSeconds()
    val initial get() = progress >= 1f

    data class Counting(override val counter: Int, override val progress: Float) : CounterUiState()

    data class Initial(override val counter: Int, override val progress: Float) :
        CounterUiState(stop = true)

    class Pause(override val counter: Int, override val progress: Float) :
        CounterUiState(stop = true)

    class Finish(override val counter: Int, override val progress: Float = 0f) : CounterUiState()
    class NavigateBack(
        override val counter: Int = 0, override val progress: Float = 0f
    ) : CounterUiState()
}


