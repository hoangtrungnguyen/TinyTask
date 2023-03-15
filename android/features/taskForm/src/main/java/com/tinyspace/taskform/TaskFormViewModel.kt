package com.tinyspace.taskform

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.tinyspace.common.BaseViewModel
import com.tinyspace.common.SHARE_PREF
import com.tinyspace.common.UiState
import com.tinyspace.common.ViewModelState
import com.tinyspace.shared.domain.GetTagUseCase
import com.tinyspace.shared.domain.GetTodayHighlightUseCase
import com.tinyspace.shared.domain.SaveTaskUseCase
import com.tinyspace.shared.domain.exception.DatabaseException
import com.tinyspace.shared.domain.model.Tag
import com.tinyspace.shared.domain.model.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class TaskFormViewModel(
    val saveTaskUseCase: SaveTaskUseCase,
    val getTodayHighlightUseCase: GetTodayHighlightUseCase,
    val getTagUseCase: GetTagUseCase
) : BaseViewModel<TaskFormEvent, TaskFormUiState, TaskFormVMState>(), KoinComponent {

    private val sharedPreferences by inject<SharedPreferences> {
        parametersOf(SHARE_PREF)
    }

    override val initialState: TaskFormVMState = TaskFormVMState()
    override val modelState = MutableStateFlow(initialState)

    override val uiState: StateFlow<TaskFormUiState> = modelState.map(TaskFormVMState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            modelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            try {
                modelState.update {
                    it.copy(isLoading = true)
                }
                val result = getTodayHighlightUseCase()
                val tags = getTagUseCase()

                if (result == null) {
                    modelState.value = TaskFormVMState()
                } else {
                    modelState.value = TaskFormVMState(
                        step = questions.size
                    )
                }

                modelState.update {
                    it.copy(tagOptions = tags)
                }
            } catch (e: Exception) {

            } finally {
                modelState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }


    private fun create() {
        viewModelScope.launch {
            modelState.update {
                it.copy(
                    isLoading = true
                )
            }


            val task = modelState.value.run {
                Task.createNew(
                    title, description,
                    durationOptions[0],
                    tagOptions,
                    isHighlight
                )
            }


            try {
                if (task.isValid()) {
                    saveTaskUseCase(task)

                    modelState.update {
                        it.copy(
                            isDone = true
                        )
                    }
                } else {

                    viewModelScope.launch {
                        modelState.update {
                            it.copy(
                                message = "Invalid form"
                            )
                        }
                        delay(1000)
                        modelState.update {
                            it.copy(
                                message = ""
                            )
                        }
                    }

                }
            } catch (e: DatabaseException) {

            }
        }.invokeOnCompletion {
            modelState.update {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }


    override fun onEvent(event: TaskFormEvent) =
        when (event) {
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
                    selectedDuration = event.choice,
                )
            }
        }
        is TaskFormEvent.SelectTag -> {
            val selected = event.tagOption

            modelState.update {
                it.copy(selectedTag = selected)
            }
        }
        is TaskFormEvent.InputTitle -> {
            modelState.update {
                it.copy(title = event.title)
            }
        }
        is TaskFormEvent.NextStep -> {
            modelState.update { state ->
                state.copy(step = state.step + 1)
            }
        }
        TaskFormEvent.Done -> {

        }
    }
}


private val DURATION_OPTIONS = listOf<Duration>(
    30.toDuration(DurationUnit.MINUTES),
    60.toDuration(DurationUnit.MINUTES),
    90.toDuration(DurationUnit.MINUTES),
    120.toDuration(DurationUnit.MINUTES),
)

data class TaskFormVMState(
    val durationOptions: List<Duration> = DURATION_OPTIONS,
    val selectedDuration: Int? = null,
    val title: String = "",
    val description: String = "",
    val tagOptions: List<Tag> = emptyList(),
    val isLoading: Boolean = false,
    val isDone: Boolean = false,
    val step: Int = 0,
    val isHighlight: Boolean = false,
    val selectedTag: Int? = null,
    val message: String = "",
) : ViewModelState<TaskFormUiState> {
    // can only call one time
    override fun toUiState(): TaskFormUiState {
        return if (step < questions.size) {
            TaskFormUiState.Intro(
                step = step
            )
        } else {
            TaskFormUiState.Editing(
                isDone = isDone,
                selectedTag = selectedTag,
                tagOptions = tagOptions,
                isLoading = isLoading,
                description = description,
                title = title,
                durationOptions = durationOptions,
                selectedDuration = selectedDuration ?: 1,
                message = message,
                headline = if (isHighlight) R.string.highlight else R.string.prioritize_task
            )
        }
    }
}


sealed class TaskFormUiState(
    open val isLoading: Boolean,
    open val isDone: Boolean,
    open val message: String
) : UiState {
    data class Intro(
        val step: Int
    ) : TaskFormUiState(
        isLoading = false,
        isDone = false,
        message = "",
    )

    data class Editing(
        val selectedTag: Int?,
        val tagOptions: List<Tag>,
        val selectedDuration: Int,
        val durationOptions: List<Duration>,
        val title: String,
        val description: String,
        val headline: Int,
        override val isLoading: Boolean,
        override val isDone: Boolean,
        override val message: String
    ) : TaskFormUiState(
        isLoading = isLoading,
        isDone = isDone,
        message = message
    )

}




