package com.tinyspace.tinytask

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CommonGreetingTest {

    @Test
    fun testFoo() = runTest {
        launch {
            println(1)   // executes during runCurrent()
            delay(1_000) // suspends until time is advanced by at least 1_000
            println(2)   // executes during advanceTimeBy(2_000)
            delay(500)   // suspends until the time is advanced by another 500 ms
            println(3)   // also executes during advanceTimeBy(2_000)
            delay(5_000) // will suspend by another 4_500 ms
            println(4)   // executes during advanceUntilIdle()
        }
        // the child coroutine has not run yet
        runCurrent()
        // the child coroutine has called println(1), and is suspended on delay(1_000)
        advanceTimeBy(2_000) // progress time, this will cause two calls to `delay` to resume
        // the child coroutine has called println(2) and println(3) and suspends for another 4_500 virtual milliseconds
        advanceUntilIdle() // will run the child coroutine to completion
        assertEquals(6500, currentTime) // the child coroutine finished at virtual time of 6_500 milliseconds
    }
}