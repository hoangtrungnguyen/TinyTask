package com.tinyspace.tinytask.android.ui.counter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyspace.tinytask.android.formatSeconds
import com.tinyspace.tinytask.domain.AppCountDownTimer
import com.tinyspace.tinytask.domain.SaveTaskUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


val INTERVAL = 1.toDuration(DurationUnit.SECONDS)
val ZERO = 0.toDuration(DurationUnit.SECONDS)
//val TOTAL = 1.toDuration(DurationUnit.MINUTES)
val TOTAL = 10.toDuration(DurationUnit.SECONDS)

const val TAG = "CounterViewModel"

private val initialState = CounterViewModelState(stop = true, current = TOTAL)

class CounterViewModel(
    private val saveTaskUseCase:SaveTaskUseCase = SaveTaskUseCase()
) : ViewModel() {

    private val modelState = MutableStateFlow(initialState)

    val uiState  = modelState.map (CounterViewModelState::toUiState)
        .stateIn(viewModelScope,
            SharingStarted.Eagerly,
        modelState.value.toUiState())

    private var counterJob: Job = Job().apply { complete() }
    private var countDownFlow: Flow<Duration>

    init {
        countDownFlow = createCountDownJob(modelState.value.total)
    }

    private fun start() {
        //initial start
        counterJob = countDownFlow.launchIn(viewModelScope)
    }

    private fun stop() {
        counterJob.cancel("Counter Stop")
    }

    private fun resume() {
        countDownFlow = createCountDownJob(this.modelState.value.current)
        counterJob = countDownFlow.launchIn(viewModelScope)
    }

    private fun createCountDownJob(started: Duration) : Flow<Duration> {
      return AppCountDownTimer.create(
            started,
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
            //TODO: Save data
        }
    }

    fun onEvent(event:CounterEvent) = when(event){
        CounterEvent.Finish -> finish()
        CounterEvent.Start -> start()
        CounterEvent.Resume -> resume()
        CounterEvent.Stop -> stop()
        CounterEvent.Restart -> {
            modelState.update { initialState }
        }
    }
}

data class CounterViewModelState(
    val total: Duration = TOTAL,
    val current: Duration,
    val stop: Boolean,
    val taskId: String = "",
    ) {

    private val initial: Boolean get() = total == current
    private val progress : Float get() = (current / total).toFloat()
    private val finish: Boolean get() = current <= ZERO

    fun toUiState(): CounterUiState = when {
        finish -> CounterUiState.Finish(ceil(current.toDouble(DurationUnit.SECONDS)).toInt())
        stop -> CounterUiState.Pause(ceil(current.toDouble(DurationUnit.SECONDS)).toInt(), progress)
        initial -> CounterUiState.Initial(total.toInt(DurationUnit.SECONDS), progress)
        else -> CounterUiState.Counting(ceil(current.toDouble(DurationUnit.SECONDS)).toInt(), progress)
    }

    fun toDomainModel() {
        TODO("Not in progress")
    }
}

sealed class CounterUiState(
    val stop: Boolean = false,
) {
    abstract val counter: Int
    abstract val progress: Float
    val finish: Boolean get () = counter <= 0
    val timer: String get() = counter.formatSeconds()
    val initial get() = progress >= 1f
    data class Counting( override val counter: Int, override val progress: Float) : CounterUiState()

    data class Initial(override val counter: Int, override val progress: Float ) :CounterUiState(stop = true)

    class Pause(override val counter: Int, override val progress: Float) : CounterUiState(stop = true)

    class Finish(override val counter: Int, override val progress: Float = 0f) : CounterUiState()

}


