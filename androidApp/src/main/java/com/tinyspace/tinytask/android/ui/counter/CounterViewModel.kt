package com.tinyspace.tinytask.android.ui.counter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyspace.tinytask.android.formatSeconds
import com.tinyspace.tinytask.domain.AppCountDownTimer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


val INTERVAL = 1.toDuration(DurationUnit.SECONDS)
val ZERO = 0.toDuration(DurationUnit.SECONDS)
val TOTAL = 1.toDuration(DurationUnit.MINUTES)

const val TAG = "CounterViewModel"

class CounterViewModel() : ViewModel() {

    val modelState = MutableStateFlow(CounterViewModelState(stop = true, current = TOTAL))

    val uiState  = modelState.map (CounterViewModelState::toUiState)
        .stateIn(viewModelScope,
            SharingStarted.Eagerly,
        modelState.value.toUiState())

    private var counterJob: Job = Job().apply { complete() }

    fun start() {
        if (counterJob.isActive) return
        if (counterJob.isCompleted && modelState.value.finish) return
        counterJob = createCountDownJob(modelState.value.total)
    }

    fun stop() {
        counterJob.cancel("Counter Stop")
    }

    fun resume() {
        counterJob = createCountDownJob(this.modelState.value.current)
    }

    private fun createCountDownJob(started: Duration) : Job{
      return AppCountDownTimer.create(
            started,
            INTERVAL
        ).onCompletion {
            if(it != null) return@onCompletion
            Log.d(TAG, "Counter finished")
        }.onEach {
            modelState.update { state ->
                state.copy(current = it)
            }
        }.catch {
            if (it is CancellationException) {
                Log.d(TAG, "Counter stopped")
            }
        }.launchIn(viewModelScope)
    }
}

data class CounterViewModelState(
    val total: Duration = TOTAL,
    val current: Duration,
    val stop: Boolean,
    ) {

    val initial: Boolean get() = total == current
    val progress : Float get() = (current / total).toFloat()
    val finish: Boolean get() = current <= ZERO

    fun toUiState(): CounterUiState {
        if (finish) return CounterUiState.Finish(0)

        if(stop) return CounterUiState.Pause(current.toInt(DurationUnit.SECONDS), progress)

        if(initial) return CounterUiState.Initial(total.toInt(DurationUnit.SECONDS), progress)

        return CounterUiState.Counting(  current.toInt(DurationUnit.SECONDS), progress)
    }
}

sealed interface CounterUiState {

    val counter: Int
    val progress: Float
    val finish: Boolean get () = counter <= 0
    val timer: String get() = counter.formatSeconds()

    data class Counting( override val counter: Int, override val progress: Float ) : CounterUiState

    data class Initial(override val counter: Int, override val progress: Float ) :CounterUiState

    class Pause(override val counter: Int, override val progress: Float) :CounterUiState

    class Finish(override val counter: Int, override val progress: Float = 0f) : CounterUiState

}


