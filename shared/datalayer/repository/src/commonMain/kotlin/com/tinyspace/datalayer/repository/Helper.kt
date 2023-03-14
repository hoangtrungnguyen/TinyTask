package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.repository.model.Tag

internal fun DatabaseHelper.mapTagToTask(
    taskId: String,
    defaultTags: List<com.tinyspace.datalayer.local.db.Tag> = emptyList()
): List<Tag> {
    val tags = defaultTags.ifEmpty { getAllTag() }
    val relations = getTaskTagRelation(taskId)
    return tags.filter { tag ->
        relations.any { relation -> relation.tagId == tag.id }
    }.map { tag ->
        Tag(
            code = tag.id,
            name = tag.name
        )
    }
}
