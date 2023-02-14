package com.tinyspace.domain.model

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlin.time.Duration


data class Task(
    val uuid          : Uuid,
    val title       : String,
    val description : String,
    val dueDate     : String,
    val isCompleted : Boolean,
    val timeSpent   : Duration,
    val tags    : List<Tag> = emptyList()
){
    companion object {
        fun createNew(
            title: String = "",
        description: String = ""
        ) = Task(
            uuid4(),
            title,
            description,
            "",
            false,
            Duration.ZERO,
        )

    }

    fun uuid(): String {
        println(uuid.toString())
        return uuid.toString()
    }
}

data class Tag(
    val name : String,
    val code : Int
){

}
