package com.tinyspace.tinytask.counter

import android.hardware.Sensor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyspace.common.BaseUiState
import com.tinyspace.common.BaseViewModelState
import com.tinyspace.common.Event
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random
import kotlin.random.nextInt


class CounterSensorVM : ViewModel() {

    private val _modelState: MutableStateFlow<SensorVMState> = MutableStateFlow(SensorVMState(0L))
    private val modeState: StateFlow<SensorVMState> = _modelState

    val uiState: StateFlow<SensorVMUi> = modeState.map(SensorVMState::toUiState).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        SensorVMUi(false)
    )

    private val sensorFlow: Flow<Long> = flow {
        val random = Random(1)
        while (true) {
            delay(100)
            emit(random.nextInt(1..100).toLong())
        }
    }


    private val otherScope = viewModelScope + Job()


    init {
        otherScope.launch {
            sensorFlow.collectLatest {
                println("Sensor data $it")
                _modelState.update { state ->
                    state.copy(
                        rotation = it
                    )
                }
            }
        }
    }

    fun watchDeviceMovement() {

    }

    fun setUpInitializeLocation() {

    }


    fun emitEvent(event: CounterSensorEvent) {
        when (event) {
            is CounterSensorEvent.OpenDialog -> TODO()
        }
    }

    override fun onCleared() {
        otherScope.cancel(CancellationException("Cleared viewModel"))
        super.onCleared()
    }

    fun onEvent(stopSensor: SensorVMEvent) {
        when (stopSensor) {
            is SensorVMEvent.StopSensor -> {
                otherScope.cancel(CancellationException("Stop"))
                _modelState.update {
                    it.copy(rotation = 0L)
                }
            }

        }
    }

    fun startSensor(sensor: Sensor?) {
        sensor?.let {
//            it.
        }
    }


}

sealed interface SensorVMEvent : Event.VMEvent {
    object StopSensor : SensorVMEvent
}

data class SensorVMState(
    val rotation: Long,
) : BaseViewModelState<SensorVMUi> {

    override fun toUiState(): SensorVMUi {
//        return SensorVMUi( rotation <= 50)
        if (rotation == 0L)
            return SensorVMUi(false)

        return SensorVMUi(true)
    }

}

data class SensorVMUi(
    val shownWarning: Boolean
) : BaseUiState

sealed interface CounterSensorEvent : Event.UiEvent {
    class OpenDialog : CounterSensorEvent

}


