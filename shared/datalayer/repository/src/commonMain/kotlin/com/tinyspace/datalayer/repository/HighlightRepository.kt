package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.repository.model.Task

interface HighlightRepository {

    suspend fun getTodayHighlight(): Task?
    suspend fun saveTodayHighlight(taskId: String, today: Long)
}