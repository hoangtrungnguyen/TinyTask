package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTotalDurationTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(): Long = taskRepository.getTotalDuration()
    fun invokeAsFlow(): Flow<Int> = taskRepository.getTotalDurationAsFlow().map(Long::toInt)
}