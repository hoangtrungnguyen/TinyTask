package com.tinyspace.taskform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyspace.shared.domain.SaveTaskUseCase
import com.tinyspace.shared.domain.exception.InsertErrorException
import com.tinyspace.shared.domain.model.Tag
import com.tinyspace.shared.domain.model.Task
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class TaskFormViewModel( val saveTaskUseCase: SaveTaskUseCase): ViewModel(), KoinComponent {

    private val initialState: ViewModelState = ViewModelState(
        0 to 0.toDuration(DurationUnit.MINUTES), "", "", emptyList(),
        isLoading = false
    )

    private val modelState = MutableStateFlow(initialState)

    val uiState: StateFlow<TaskFormUiState> = modelState.map(ViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            modelState.value.toUiState()
        )


    private fun create() {
        viewModelScope.launch {
            modelState.update {
                it.copy(
                    isLoading =  true
                )
            }
            val task = modelState.value.run {
                Task.createNew(
                    title, description, durations[durationOption.first],
                    tags
                )
            }
            saveTaskUseCase(task)
        }.invokeOnCompletion {completable ->

            when(completable){
                null -> {
                    modelState.update {
                        it.copy(
                            isLoading =  false,
                        ).apply {
                            isDone = true
                        }
                    }
                }
                is InsertErrorException -> {
                    modelState.update {
                        it.copy(
                            isLoading =  false,
                        )
                    }
                }
                is CancellationException -> {

                }
                else -> {

                }
            }
        }
    }


    internal fun onEvent(event: TaskFormEvent) = when (event) {
        is TaskFormEvent.CreateTask -> {
            create()
        }
        is TaskFormEvent.InputDescription -> {
            modelState.update { state ->
                state.copy(description = event.description)
            }
        }
        is TaskFormEvent.SelectDuration -> {
            modelState.update { state ->
                state.copy(
                    durationOption = Pair<Int, Duration>(
                        event.choice, durations[event.choice]
                    )
                )
            }
        }
        is TaskFormEvent.SelectTag -> {
            val tagOption = event.tagOption

            modelState.update {
                val innerTagUis = mutableListOf<Tag>()
                var isContain = false
                for (tagUi: Tag in it.tags) {
                    if (tagUi != defaultTagUis[tagOption]) {
                        innerTagUis.add(tagUi)
                    } else {
                        isContain = true
                    }
                }
                if (!isContain) {
                    innerTagUis.add(defaultTagUis[tagOption])
                }

                it.copy(tags = innerTagUis)
            }
        }
        is TaskFormEvent.InputTitle -> {
            modelState.update {
                it.copy(title = event.title)
            }
        }
        TaskFormEvent.Done -> {

        }
    }
}


private data class ViewModelState(
    val durationOption: Pair<Int, Duration>,
    val title: String,
    val description: String,
    val tags: List<Tag>,
    val isLoading: Boolean,
    private var _isDone: Boolean = false

) {


    // can only call one time
    var isDone: Boolean
        set(value)  {
            assert(!_isDone)
            _isDone = true
        }
    get() = _isDone

    fun toUiState(): TaskFormUiState {
        return TaskFormUiState(
            durationOption = durationOption.first,
            description = description,
            tagUis = tags,
            title = title,
            isLoading = isLoading,
            isDone = isDone
        )
    }
}



data class TaskFormUiState(
    val durationOption: Int = 0,
    val title: String = "",
    val description: String = "",
    val tagUis: List<Tag> = emptyList(),
    val isLoading: Boolean = false,
    val isDone: Boolean = false
)


internal val defaultTagUis = listOf(
    Tag("Personal", "1"),
    Tag("Work", "2"),
    Tag("Relation", "3"),
    Tag("Home", "4"),
)


private val durations = listOf<Duration>(
    30.toDuration(DurationUnit.MINUTES),
    60.toDuration(DurationUnit.MINUTES),
    90.toDuration(DurationUnit.MINUTES),
    120.toDuration(DurationUnit.MINUTES),
)
