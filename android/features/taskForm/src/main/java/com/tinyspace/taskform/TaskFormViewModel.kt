package com.tinyspace.taskform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class TaskFormViewModel(

) : ViewModel() {
//    final val saveTaskUseCase: SaveTaskUseCase by inject(
//
//    )
    private val initialState: ViewModelState = ViewModelState(
        0 to 0.toDuration(DurationUnit.MINUTES), "", "", emptyList()
    )

    private val modelState = MutableStateFlow(initialState)

    val uiState = modelState.map(ViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            modelState.value.toUiState()
        )


    private fun create() {
        viewModelScope.launch {
//            saveTaskUseCase(Any())
        }
    }


    internal fun onEvent(event: TaskFormEvent) = when (event) {
        is TaskFormEvent.Create -> {
            create()
        }
        is TaskFormEvent.DescriptionInput -> {
            modelState.update { state ->
                state.copy(description = event.description)
            }
        }
        is TaskFormEvent.DurationSelected -> {
            modelState.update { state ->
                state.copy(
                    durationOption = Pair<Int, Duration>(
                        event.choice, durations[event.choice]
                    )
                )
            }
        }
        is TaskFormEvent.OnTagSelected -> {
            val tagOption = event.tagOption

            modelState.update {
                val innerTags = mutableListOf<Tag>()
                var isContain = false
                for (tag: Tag in it.tags) {
                    if (tag != defaultTags[tagOption]) {
                        innerTags.add(tag)
                    } else {
                        isContain = true
                    }
                }
                if (!isContain) {
                    innerTags.add(defaultTags[tagOption])
                }

                it.copy(tags = innerTags)
            }
        }
        is TaskFormEvent.TitleInput -> {
            modelState.update {
                it.copy(title = event.title)
            }
        }
    }
}


private data class ViewModelState(
    val durationOption: Pair<Int, Duration>,
    val title: String,
    val description: String,
    val tags: List<Tag>
) {

    fun toUiState(): TaskFormUiState {
        return TaskFormUiState(
            durationOption = durationOption.first,
            description = description,
            tags = tags,
            title = title
        )
    }
}


data class Tag(
    val name: String,
    val code: Int
)

data class TaskFormUiState(
    val durationOption: Int = 0,
    val title: String = "",
    val description: String = "",
    val tags: List<Tag> = emptyList()
) {


}


internal val defaultTags = listOf(
    Tag("Work", 1),
    Tag("Personal", 2),
    Tag("Workout", 3),
    Tag("Coding", 4),
)


private val durations = listOf<Duration>(
    30.toDuration(DurationUnit.MINUTES),
    60.toDuration(DurationUnit.MINUTES),
    90.toDuration(DurationUnit.MINUTES),
    120.toDuration(DurationUnit.MINUTES),
)
