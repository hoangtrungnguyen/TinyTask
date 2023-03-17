package com.tinyspace.tinytask.counter.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CounterSensor(context: Context) {
    private var sensorManager: SensorManager

    private var sensor: Sensor

    private var applicationContext: Context? = null

    private val mRotationMatrix = FloatArray(16)

    private val orientationMatrix = FloatArray(3)


    init {
        applicationContext = context.applicationContext
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }


    fun createFlow(): Flow<FloatArray> {
        return callbackFlow<FloatArray> {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {

                    if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                        SensorManager.getQuaternionFromVector(mRotationMatrix, event.values)
//                        Log.d("MY_APP",event.values.joinToString(", ") )
//                        Log.d("MY_APP Rotation Matrix",mRotationMatrix.joinToString(", ") )
                        trySendBlocking(mRotationMatrix.copyOf())
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//                    SensorManager.SENSOR_STATUS_ACCURACY_LOW
//                    if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
//                        SensorManager.getQuaternionFromVector(mRotationMatrix, event.values)
////                        Log.d("MY_APP",event.values.joinToString(", ") )
////                        Log.d("MY_APP Rotation Matrix",mRotationMatrix.joinToString(", ") )
//                        trySendBlocking(mRotationMatrix.copyOf())
//                    }
                }

            }

            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)

            awaitClose {
                sensorManager.unregisterListener(listener)
            }
        }
    }
}
