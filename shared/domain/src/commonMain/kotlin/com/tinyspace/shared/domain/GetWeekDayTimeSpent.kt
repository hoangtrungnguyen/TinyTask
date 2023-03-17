package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

class GetWeekDayTimeSpent(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke() {

//        val clock = Clock.System.now()
//
//        val c = LocalDate.getInstance()
//
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)
//
//        val hour = c.get(Calendar.HOUR_OF_DAY)
//        val minute = c.get(Calendar.MINUTE)


        val current = Clock.System.now().epochSeconds

        LocalDate.fromEpochDays((current / 86400).toInt())

        println("Localdate current ${LocalDate.fromEpochDays((current / 24).toInt())}")
        println("Current $current")
    }
}