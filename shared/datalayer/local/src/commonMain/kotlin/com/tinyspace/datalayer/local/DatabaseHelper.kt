package com.tinyspace.datalayer.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.db.SqlDriver
import com.tinyspace.datalayer.local.db.CountTotalDuration
import com.tinyspace.datalayer.local.db.Tag
import com.tinyspace.datalayer.local.db.Task
import com.tinyspace.datalayer.local.db.TaskTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class DatabaseHelper(
    sqlDriver: SqlDriver,
) {

    private val dbRef: TaskDb = TaskDb(sqlDriver)


    fun insertTask(
        id: String, title: String, description: String,
        completed: Long, createdTime: Long, duration: Long
    ) {

        runBlocking(IOScope) {
            // Launch a coroutine to perform the I/O operation
            dbRef.taskQueries.insertTask(
                id, title, description, completed, createdTime, duration
            )
        }

    }

    fun getTaskTagRelation(taskId: String): List<TaskTag> {
        return runBlocking(IOScope) {
            dbRef.taskTagQueries.getRelation(
                taskId
            ).executeAsList()
        }
    }

    fun getAllTag(): List<Tag> =
        runBlocking(IOScope) {
            dbRef.tagQueries.getAll().executeAsList()
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

    fun countTotalDuration(): CountTotalDuration {
        return runBlocking(IOScope) {
            dbRef.taskQueries.countTotalDuration().executeAsOne()
        }
    }

    fun setCompleted(taskId: String) {
        return dbRef.taskQueries.setCompleted(
            id = taskId,
            completed = 1
        )
    }

    fun getById(id: String): Task {
        return runBlocking<Task>(IOScope) {
            // Launch a coroutine to perform the I/O operation
            dbRef.taskQueries.getById(id = id).executeAsOne()
        }
    }

    fun countAll(): Long {
        return runBlocking(IOScope) {
            dbRef.taskQueries.countAll().executeAsOne()
        }
    }

    fun countCompleted(): Long {
        return runBlocking(IOScope) {
            dbRef.taskQueries.countCompleted().executeAsOne()
        }
    }

    fun countCompletedAsFlow(): Flow<Long> {
        return dbRef.taskQueries.countCompleted().asFlow().mapToOne(IOScope)
    }

    fun countUnfinished(): Long {
        return runBlocking(IOScope) {
            dbRef.taskQueries.countUnfinished().executeAsOne()
        }
    }

    fun countAllAsFlow(): Flow<Long> {
        return dbRef.taskQueries.countAll().asFlow().mapToOne(IOScope)
    }

    fun insertRelation(taskId: String, listTagId: List<String>) {
        return runBlocking(IOScope) {
            dbRef.transaction {
                for (id in listTagId) {
                    dbRef.taskTagQueries.insertRelation(taskId, id)
                }
            }
        }
    }

}




