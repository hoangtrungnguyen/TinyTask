package com.tinyspace.datalayer.local

import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DatabaseHelper(
    sqlDriver: SqlDriver,
) {

    private val dbRef: TaskDb = TaskDb(sqlDriver)


    fun insertTask(id: String, title: String, description: String,
                   completed: Long, createdTime: Long, duration: Long){

        //TODO: Handle exception here
        runBlocking(IOScope) {
            // Launch a coroutine to perform the I/O operation
            dbRef.taskQueries.insertTask(
                id, title, description, completed, createdTime, duration
            )
        }
    }
}




