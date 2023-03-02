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
import com.tinyspace.tinytask.counter.sensor.CounterSensor
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
private val TOTAL = 10.toDuration(DurationUnit.SECONDS)

const val TAG = "CounterViewModel"

const val EmptyMessage = ""

class CounterViewModel(
    private val taskId: String,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    counterSensor: CounterSensor
) : BaseViewModel<CounterEvent, CounterUiState, CounterVMState>(), KoinComponent {

    override val initialState: CounterVMState = CounterVMState(
        current = ZERO, total = ZERO, isLoading = true,
        message = EmptyMessage,
        state = CounterState.Initialize
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
        startSensor()
    }

    private fun stop() {
        counterJob.cancel("Counter Stop")
    }

    private fun resume() {
        if (this.modelState.value.state == CounterState.Initialize) return
        if (this.modelState.value.state == CounterState.Counting) return
        countDownFlow = createCountDownJob(modelState.value.current)
        counterJob = countDownFlow.launchIn(viewModelScope)
    }

    private fun createCountDownJob(total: Duration) : Flow<Duration> {
      return AppCountDownTimer.create(
            total,
            INTERVAL
        ).onStart {
          modelState.update { state ->
              state.copy(state = CounterState.Counting)
          }
        }.onCompletion {
            if(it != null) {
                modelState.update { state ->
                    state.copy(state = CounterState.Pause)
                }
            } else {
                Log.d(TAG, "Counter finished")
                modelState.update { state ->
                    state.copy(state = CounterState.Finished)
                }
                stopSensor()
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
                it.copy(total = TOTAL, current = TOTAL)
            }
        }
        CounterEvent.Cancel -> {
            modelState.update {
                it.copy(
                    isNavigateBack = true
                )
            }
        }
    }


    private val sensorFlow: Flow<FloatArray> = counterSensor.createFlow()
        .stateIn(viewModelScope,
            SharingStarted.Lazily,
            FloatArray(16) { 0f }
        )

    private val otherScope = viewModelScope + Job()


    private fun stopSensor() {
        otherScope.cancel("Stop Event")
    }

    private fun startSensor() {
        sensorFlow
            .debounce(100)
            .onEach {
//                println("Matrix Rotation ${it.joinToString(", ")}")
//                println("Matrix Rotation ${it[3]}")
                if (it[3] > 9.4 || it[3] < -9.4) {
                    onEvent(CounterEvent.Resume)
                } else {
                    onEvent(CounterEvent.Stop)
                }
            }.launchIn(otherScope)
    }
}

data class CounterVMState(
    val state: CounterState,
    val total: Duration = TOTAL,
    val current: Duration,
    val taskId: String = "",
    val title: String = "",
    val isNavigateBack: Boolean = false,
    val isLoading: Boolean,
    val message: String,
    val tags: List<Tag> = emptyList()
) : BaseViewModelState<CounterUiState> {

    private val progress: Float get() = (current / total).toFloat()
    private val finish: Boolean get() = total > ZERO && current <= ZERO

    override fun toUiState(): CounterUiState =
        CounterUiState(
            state = state,
            tags = this@CounterVMState.tags.map { it.name },
            title = this@CounterVMState.title,
            progress = this@CounterVMState.progress,
            counter = current.durationToLong(),
            isNavigateBack = isNavigateBack
        )

    private fun Duration.durationToLong(): Int {
        return ceil(this.toDouble(DurationUnit.SECONDS)).toInt()
    }

    fun toDomainModel(): DomainTask {
        return DomainTask.instanceForUpdate(taskId, finish)
    }
}

class CounterUiState(
    var message: String = "",
    var title: String = "",
    var tags: List<String> = emptyList(),
    val state: CounterState,
    var counter: Int = 0,
    var progress: Float = 0f,
    val isNavigateBack: Boolean = false
) : BaseUiState {
    val timer: String get() = counter.formatSeconds()
}

enum class CounterState {
    Pause,
    Counting,
    Finished,
    Initialize,
}

