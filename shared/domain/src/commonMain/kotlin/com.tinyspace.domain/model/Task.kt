package com.tinyspace.domain.model

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidFrom
import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import com.tinyspace.datalayer.local.db.Task as TaskDTO


data class Task(
    private val _uuid: Uuid,
    val title: String,
    val description: String,
    val dueDate: Long = -1L, //format 2023-12-02
    private val _completed: Boolean,
    val duration: Duration,
    val createdTime: Long,
    val tags: List<Tag> = emptyList()
) {
    internal fun toDTO(): TaskDTO {
        return TaskDTO(
            uuid,
            title,
            description,
            completed,
            createdTime,
            duration.toLong(DurationUnit.SECONDS),
            dueDate
        )
    }

    companion object {
        fun createNew(
            title: String = "",
            description: String = "",
            duration: Duration = 30.toDuration(DurationUnit.MINUTES)
        ) = Task(
            _uuid = uuid4(),
            title,
            description,
            dueDate = Clock.System.now().epochSeconds,
            false,
            duration = duration,
            createdTime = Clock.System.now().epochSeconds
        )

        fun fromDb(task: TaskDTO): Task {
            return Task(
                _uuid = uuidFrom(task.id),
                task.title,
                task.description,
                dueDate = task.dueDate ?: -1L,
                false,
                duration = task.duration.toDuration(DurationUnit.SECONDS),
                createdTime = Clock.System.now().epochSeconds
            )
        }
    }

    val uuid: String get() = _uuid.toString()
    val completed : Long get () = if(_completed) 1L else 0L
}

data class Tag(
    val name : String,
    val code : Int
)
