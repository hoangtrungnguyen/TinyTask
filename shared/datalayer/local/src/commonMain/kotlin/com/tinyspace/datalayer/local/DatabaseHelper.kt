package com.tinyspace.datalayer.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.tinyspace.datalayer.local.db.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class DatabaseHelper(
    sqlDriver: SqlDriver,
) {

    private val dbRef: TaskDb = TaskDb(sqlDriver)


    fun insertTask(id: String, title: String, description: String,
                   completed: Long, createdTime: Long, duration: Long
    ) {

        //TODO: Handle exception here
        runBlocking(IOScope) {
            // Launch a coroutine to perform the I/O operation
            dbRef.taskQueries.insertTask(
                id, title, description, completed, createdTime, duration
            )
        }
    }

    fun watchRecentTask(): Flow<List<Task>> {
        // Launch a coroutine to perform the I/O operation
        return dbRef.taskQueries.getMostRecent().asFlow().mapToList(
            IOScope
        )
    }


    fun getRecentTasks(): List<Task> {
        return dbRef.taskQueries.getMostRecent().executeAsList()
    }

    fun setCompleted(task: Task) {
        return dbRef.taskQueries.setCompleted(
            id = task.id,
            completed = task.completed
        )
    }
}




