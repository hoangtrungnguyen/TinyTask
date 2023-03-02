package com.tinyspace.tinytask.counter

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.compose.TinyTaskTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(
    taskId: String,
    counterVM: CounterViewModel = koinViewModel {
        parametersOf(
            taskId
        )
    },
    onNavigateBack: () -> Boolean
) {
    val counterUi by counterVM.uiState.collectAsState()


    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    when (counterUi.state) {
        CounterState.Counting -> BackHandler(true) {}
        else -> BackHandler(false) {}
    }


    Scaffold(
        topBar = {
            AnimatedVisibility(
                visibleState = remember {
                    MutableTransitionState(
                        counterUi.state != CounterState.Initialize
                    )
                }
                    .apply { targetState = counterUi.state == CounterState.Initialize },
                enter = slideInVertically(
                    initialOffsetY = { -40 }
                ) + expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically() + shrinkVertically() + fadeOut(),

                ) {
                CounterTopAppBar(
                    counterUi.title,
                    onNavigateBack = onNavigateBack
                )
            }
        }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Category(counterUi.tags)

                CounterView(time = counterUi.timer, progress = counterUi.progress.progress())

                Actions(counterUi.state, counterVM)
            }
    }

    SnackbarHost(hostState = snackbarHostState)


    if (counterUi.state == CounterState.Pause) {
        WarningDialog {
            onNavigateBack()
        }

    } else if (counterUi.state != CounterState.Initialize) {
        counterVM.onEvent(CounterEvent.Resume)
    }
    if (counterUi.isNavigateBack) {
        onNavigateBack()
    }

    if (counterUi.message.isNotEmpty()) {
        LaunchedEffect(scope) {
            snackbarHostState.showSnackbar(
                counterUi.message
            )
        }
    }


}

private fun Float.progress(): Float = if (isNaN()) 0f else this

@Composable
private fun Actions(
    state: CounterState,
    viewModel: CounterViewModel,
) {

    when (state) {
        CounterState.Pause -> {
            Box {}
        }
        CounterState.Counting -> {
            Box {}
        }
        CounterState.Finished -> {
            Column(verticalArrangement = Arrangement.Center) {
                Button(
                    onClick = { viewModel.onEvent(CounterEvent.Finish) },
                    modifier = Modifier.width(width = 256.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(stringResource(R.string.finish))
                }/*
                TextButton(
                    onClick = { viewModel.onEvent(CounterEvent.Restart) },
                    modifier = Modifier.width(width = 256.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(stringResource(R.string.restart))
                }*/
            }
        }
        CounterState.Initialize -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                ActionButton(
                    enabled = true,
                    onClick = {
                        viewModel.onEvent(CounterEvent.Start)
                    },
                    title = stringResource(id = R.string.start)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_play),
                        contentDescription = stringResource(R.string.resume)
                    )
                }
            }
        }
    }
}

@Composable
private fun Category(tags: List<String>) {
    Column(horizontalAlignment = Alignment.Start) {
        for (tag in tags) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RingIndicator(size = 16f)
                Box(Modifier.width(12.dp))
                Text(tag)
            }
        }
    }
}


@Composable
fun ActionButton(
    title: String,
    onClick: () -> Unit,
    enabled: Boolean,
    icon: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        FilledIconButton(
            enabled = enabled,
            onClick = onClick, colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            icon()
        }
        Text(title, style = MaterialTheme.typography.labelMedium)
    }


}


@ExperimentalMaterial3Api
@Composable
private fun CounterTopAppBar(
    title: String,
    onNavigateBack: () -> Boolean
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(Icons.Default.ArrowBack, "Back Button")
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(title, fontWeight = FontWeight.Medium)
            }
        },
        actions = {
            Box(Modifier.width(48.dp))
        }
    )
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun ActionsTabPreview() {
    TinyTaskTheme {
        Surface {
//            Actions(stop = true, finish = false, initial = true, viewModel = viewModel())
        }
    }
}