package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.repository.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HighlightRepositoryImpl(
    private val databaseHelper: DatabaseHelper
) : HighlightRepository {

    override suspend fun getTodayHighlight(): Task? {
        val row = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toSQLRow()
        val todayHighlight = databaseHelper.getTodayHighlight(row)
        return todayHighlight?.let {
            Task.fromDBModel(data = it, databaseHelper.mapTagToTask(it.id))
        }
    }

    override suspend fun saveTodayHighlight(taskId: String, datetime: Long) {
        val timeFromEpoch = Instant.fromEpochSeconds(datetime)
        val localDateTime = timeFromEpoch.toLocalDateTime(TimeZone.currentSystemDefault())
        val row = localDateTime.toSQLRow()
        databaseHelper.saveTodayHighlight(taskId = taskId, day = row)
    }
}