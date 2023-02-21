package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class CountType {
    All,
    COMPLETED,
    UNFINISHED,
}
class CountTaskUseCase(
    private val taskRepositoryImpl: TaskRepository
) {

    suspend operator fun invoke(type: CountType): Int {
        val count = when (type) {
            CountType.All -> taskRepositoryImpl.countAll()
            CountType.COMPLETED -> taskRepositoryImpl.countCompleted()
            CountType.UNFINISHED -> taskRepositoryImpl.countUnfinished()
        }
        return count.toInt()
    }

    fun invokeFlow(type: CountType): Flow<Int> =
        when (type) {
            CountType.All -> taskRepositoryImpl.countAllAsFlow().map(Long::toInt)
            CountType.COMPLETED -> taskRepositoryImpl.countCompletedAsFlow().map(Long::toInt)
            CountType.UNFINISHED -> TODO("")
        }

}