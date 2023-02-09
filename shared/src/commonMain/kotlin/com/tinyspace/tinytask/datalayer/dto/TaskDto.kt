package com.tinyspace.tinytask.datalayer.dto

import kotlinx.serialization.Serializable


@Serializable
data class TaskDTO (

    var id          : Int,
    var title       : String,
    var description : String,
    var dueDate     : String,
    var isCompleted : Boolean,
    var timeSpent   : String,
    var category    : Category = Category("", -1)
)

@Serializable
data class Category (

    var name : String,
    var code : Int

)