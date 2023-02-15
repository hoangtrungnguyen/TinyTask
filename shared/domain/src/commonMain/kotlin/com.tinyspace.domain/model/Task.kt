package com.tinyspace.domain.model

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.datetime.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


data class Task(
    private val _uuid          : Uuid,
    val title       : String,
    val description : String,
    val dueDate     : String,
    private val _completed : Boolean,
    val duration   : Duration,
    val createdTime : Long,
    val tags    : List<Tag> = emptyList()
){
    internal fun toDTO(): com.tinyspace.datalayer.local.model.Task {

            return com.tinyspace.datalayer.local.model.Task(
                uuid,
                title,
                description,
                completed,
                createdTime,
                duration.toLong(DurationUnit.SECONDS)
            )

    }

    companion object {
        fun createNew(
            title: String = "",
        description: String = "",
            duration: Duration = 30.toDuration(DurationUnit.MINUTES)
        ) = Task(
            uuid4(),
            title,
            description,
            "",
            false,
            duration,
            Clock.System.now().epochSeconds
        )
    }

    val uuid: String get() = _uuid.toString()
    val completed : Long get () = if(_completed) 1L else 0L
}

data class Tag(
    val name : String,
    val code : Int
){

}
