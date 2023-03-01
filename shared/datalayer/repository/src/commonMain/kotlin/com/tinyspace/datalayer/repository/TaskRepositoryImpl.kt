package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.repository.model.Tag
import com.tinyspace.datalayer.repository.model.Task
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val databaseHelper: DatabaseHelper
) : TaskRepository {

    override suspend fun save(task: Task) {
        coroutineScope {
            databaseHelper.insertTask(
                task.id,
                task.title,
                task.description,
                0,
                task.createdDate,
                task.duration,
            )

            databaseHelper.insertRelation(
                task.id,
                task.tags.map { it.code }
            )
        }

    }


    override fun watchRecentTask(): Flow<List<Task>> {
        return databaseHelper.watchRecentTask().map { tasks ->
            mapTaskFromDb(tasks)
        }
    }

    private fun mapTaskFromDb(tasks: List<com.tinyspace.datalayer.local.db.Task>): List<Task> {
        val tags = databaseHelper.getAllTag()
        return tasks.map {
            Task.fromDBModel(
                it,
                tags = mapTagToTask(it.id, tags)
            )
        }
    }


    override suspend fun getRecentTasks(): List<Task> {
        val tasks = databaseHelper.getRecentTasks()
        return mapTaskFromDb(tasks)
    }

    private fun mapTagToTask(
        taskId: String,
        tags: List<com.tinyspace.datalayer.local.db.Tag>
    ): List<Tag> {
        val relations = databaseHelper.getTaskTagRelation(taskId)
        return tags.filter { tag ->
            relations.any { relation -> relation.tagId == tag.id }
        }.map { tag ->
            Tag(
                code = tag.id,
                name = tag.name
            )
        }
    }

    override suspend fun setCompleted(task: Task) {
        return databaseHelper.setCompleted(task.id)
    }

    override fun countAllAsFlow(): Flow<Long> {
        return databaseHelper.countAllAsFlow()
    }

    override suspend fun update(task: Task) {
    }

    override suspend fun delete(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): Task {
        val tags = databaseHelper.getAllTag()
        return Task.fromDBModel(
            databaseHelper.getById(id),
            tags = this.mapTagToTask(id, tags)
        )
    }

    override suspend fun getLimit(count: Int): List<Task> {
        val tags = databaseHelper.getAllTag()
        return databaseHelper.getLimit(count.toLong()).map {
            Task.fromDBModel(
                it,
                tags = mapTagToTask(it.id, tags)
            )
        }
    }


    override suspend fun countAll(): Long {
        return databaseHelper.countAll()
    }

    override suspend fun countCompleted(): Long {
        return databaseHelper.countCompleted()
    }

    override fun countCompletedAsFlow(): Flow<Long> {
        return databaseHelper.countCompletedAsFlow()
    }

    override suspend fun countUnfinished(): Long {
        return databaseHelper.countUnfinished()
    }

    override suspend fun getAllTask(): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun getTotalDuration(): Long {
        return databaseHelper.countTotalDuration().SUM ?: -1L
    }

    override fun getTotalDurationAsFlow(): Flow<Long> {
        return databaseHelper.countCompletedAsFlow()
    }
}