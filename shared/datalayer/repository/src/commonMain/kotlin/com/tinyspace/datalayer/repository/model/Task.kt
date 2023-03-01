package com.tinyspace.datalayer.repository.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val completed: Long,
    val createdDate: Long,
    val duration: Long,
    val dueDate: Long?,
    val tags: List<Tag>,
) {


    companion object {

        fun fromDBModel(
            data: com.tinyspace.datalayer.local.db.Task,
            tags: List<Tag> = emptyList()
        ): Task {
            return Task(
                id = data.id,
                title = data.title,
                description = data.description,
                completed = data.completed,
                createdDate = data.createdDate,
                duration = data.duration,
                dueDate = data.dueDate,
                tags = tags
            )
        }
    }


}
