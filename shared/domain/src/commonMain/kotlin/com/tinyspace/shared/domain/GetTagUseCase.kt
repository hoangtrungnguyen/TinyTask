package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TagRepository
import com.tinyspace.shared.domain.model.Tag

class GetTagUseCase(
    private val tagRepository: TagRepository
) {

    suspend operator fun invoke(): List<Tag> {
        return try {
            tagRepository.getAllTag().map {
                Tag.fromDTO(it)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}