package com.tinyspace.tinytask.counter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tinyspace.compose.TagIcon
import com.tinyspace.compose.TinyTaskTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(
    taskId: String,
    viewModel: CounterViewModel = koinViewModel {
        parametersOf(
            taskId
        )
    },
    onNavigateBack: () -> Boolean
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    AnimatedVisibility(
        true,
        enter = fadeIn(initialAlpha = 0.3f),
        exit = fadeOut(),
    ) {
        Scaffold(
            topBar = {
                CounterTopAppBar(
                    onNavigateBack = onNavigateBack
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Category()

                CounterView(time = uiState.timer, progress = uiState.progress.progress())

                Actions(uiState.stop, uiState.finish, uiState.initial, viewModel)
            }
        }
    }

    SnackbarHost(hostState = snackbarHostState)


    if (uiState is CounterUiState.NavigateBack) {
        LaunchedEffect(scope) {
            onNavigateBack()
        }
    } else if (uiState is CounterUiState.Error) {
        LaunchedEffect(scope) {
            snackbarHostState.showSnackbar(
                uiState.message
            )
        }
    }
}

private fun Float.progress(): Float = if (isNaN()) 0f else this

@Composable
private fun Actions(
    stop: Boolean,
    finish: Boolean,
    initial: Boolean,
    viewModel: CounterViewModel
) {
    if (finish) {
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
            }
            TextButton(
                onClick = { viewModel.onEvent(CounterEvent.Restart) },
                modifier = Modifier.width(width = 256.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(stringResource(R.string.restart))
            }
        }

    } else {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionButton(
                enabled = stop,
                onClick = {
                    if (initial) viewModel.onEvent(CounterEvent.Start)
                    else viewModel.onEvent(CounterEvent.Resume)
                },
                title = if (initial) stringResource(id = R.string.start) else stringResource(R.string.resume)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_play),
                    contentDescription = stringResource(R.string.resume)
                )
            }
            ActionButton(onClick = {
                viewModel.onEvent(CounterEvent.Stop)
            }, title = stringResource(R.string.pause), enabled = !stop) {
                Icon(
                    painterResource(id = R.drawable.ic_pause),
                    contentDescription = stringResource(R.string.pause)
                )
            }
        }
    }
}

@Composable
private fun Category() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RingIndicator(size = 16f)
        Box(Modifier.width(12.dp))
        Text("UI Design")
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
                Text("Rasion project", fontWeight = FontWeight.Medium)
            }
        },
        actions = {
            TagIcon(title = "Work", code = 2)
        },

        )
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun ActionsTabPreview() {
    TinyTaskTheme {
        Surface {
            Actions(stop = true, finish = false, initial = true, viewModel = viewModel())
        }
    }
}