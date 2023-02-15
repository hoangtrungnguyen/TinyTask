package com.tinyspace.datalayer.local.model


data class Task(
   val id: String,
   val title: String,
   val description: String,
   val isCompleted: Long,
   val createdTime: Long,
   val duration: Long
)