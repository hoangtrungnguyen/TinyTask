package com.tinyspace.tinytask.domain

import com.tinyspace.tinytask.SystemClock
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration



interface CountDownTimer {
    fun create(total: Duration, interval: Duration): Flow<Duration>
}

object AppCountDownTimer : CountDownTimer {
    override fun create(total: Duration, interval: Duration): Flow<Duration> = CountDownFlow(total, interval)
}


@OptIn(FlowPreview::class)
class CountDownFlow(
    private val total: Duration,
    private val interval: Duration,
//    private val time: AppCountDownTimer = AppCountDownTimer, //for tests
//    private val delayer: Delayer = KotlinDelayer, //for tests
) : AbstractFlow<Duration>() {

    private val systemClock = SystemClock()

    init {
        require(interval > Duration.ZERO)
    }

    private val elapsedTime: Duration get() = systemClock.elapsedRealtimeNanos().toDuration(DurationUnit.NANOSECONDS)

    override suspend fun collectSafely(collector: FlowCollector<Duration>) {
        val deadline = elapsedTime + total
        var left = total
        while (left > Duration.ZERO) {
            tick(collector, left)
            left = deadline - elapsedTime
        }
        collector.emit(Duration.ZERO)
    }

    private suspend fun tick(collector: FlowCollector<Duration>, left: Duration) {
        val tickDuration = measure { collector.emit(left) }

        val afterTickLeft = left - tickDuration
        val delayTime = if (afterTickLeft < interval) {
            afterTickLeft.coerceAtLeast(interval)
        } else {
           interval - tickDuration % interval
        }
        delay(delayTime)
    }

    private suspend fun measure(block: suspend () -> Unit): Duration {
        val start = elapsed()
        block()
        return elapsed() - start
    }

    private fun elapsed() = systemClock.elapsedRealtimeNanos().nanoseconds

    //copied from kotlinx.coroutines.Delay for the example
    internal fun Duration.toDelayMillis(): Long =
        if (this > Duration.ZERO) inWholeMilliseconds.coerceAtLeast(1) else 0

    operator fun Duration.rem(other: Duration) =
        (this.inWholeNanoseconds % other.inWholeNanoseconds).nanoseconds
}
