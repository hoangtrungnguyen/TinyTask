package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.repository.model.Task

class HighlightRepositoryImpl(
    private val databaseHelper: DatabaseHelper
) : HighlightRepository {

    override suspend fun getTodayHighlight(): Task? {
        val todayHighlight = databaseHelper.getTodayHighlight()
        return todayHighlight?.let {
            Task.fromDBModel(data = it, databaseHelper.mapTagToTask(it.id))
        }
    }

    override suspend fun saveTodayHighlight(taskId: String, day: String) {
        databaseHelper.saveTodayHighlight(taskId = taskId, day = day)
    }
}