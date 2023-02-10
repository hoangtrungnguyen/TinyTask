package com.tinyspace.domain.model


data class Task(
    val id          : Int,
    val title       : String,
    val description : String,
    val dueDate     : String,
    val isCompleted : Boolean,
    val timeSpent   : String,
    val category    : Category? = Category("",-1)
){

}

data class Category(
    val name : String,
    val code : Int
){

}
