package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.repository.model.Tag

class TagRepository(
    private val databaseHelper: DatabaseHelper
) {
    fun getAllTag(): List<Tag> {
        return databaseHelper.getAllTag().map { Tag(it.id, it.name) }
    }

}