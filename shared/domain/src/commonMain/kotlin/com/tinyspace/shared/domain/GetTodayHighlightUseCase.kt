package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.HighlightRepository
import com.tinyspace.shared.domain.model.Task

class GetTodayHighlightUseCase(
    private val highlightRepository: HighlightRepository
) {

    suspend operator fun invoke(): Task? {
        return try {
            val result = highlightRepository.getTodayHighlight()
            result?.let {
                Task.fromRepo(result)
            }
        } catch (ex: Exception) {
            null
        }
    }
}