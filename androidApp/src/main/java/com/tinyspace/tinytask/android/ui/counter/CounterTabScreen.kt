package com.tinyspace.tinytask.android.ui.counter

import CounterView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tinyspace.tinytask.android.R
import com.tinyspace.tinytask.android.ui.theme.TinyTaskTheme

@Composable
fun CounterTabScreen(
    tab: Int,
    viewModel: CounterViewModel = viewModel()
) {
    val isVisible = tab == 0

    val uiState by viewModel.uiState.collectAsState()

    AnimatedVisibility(
        isVisible,
        enter = fadeIn(initialAlpha = 0.3f),
        exit = fadeOut(),

        ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CounterView(time = uiState.timer, progress = uiState.progress)

                Box(modifier = Modifier.height(16.dp))

                if (uiState.finish) {
                    OutlinedButton(onClick = {

                    }, modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                        Text(stringResource(R.string.finish))
                    }

                } else {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        ActionButton(onClick = {
                            if (viewModel.modelState.value.stop) {
                                viewModel.resume()
                            } else viewModel.start()
                        }, title = stringResource(R.string.resume)) {
                            Icon(
                                painterResource(id = R.drawable.ic_play),
                                contentDescription = stringResource(R.string.resume)
                            )
                        }
                        ActionButton(onClick = {
                            viewModel.stop()
                        }, title = stringResource(R.string.pause)) {
                            Icon(
                                painterResource(id = R.drawable.ic_pause),
                                contentDescription = stringResource(R.string.pause)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButton(title: String, onClick: () -> Unit, icon: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledIconButton(
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        ) {
            icon()
        }
        Text(title, style = MaterialTheme.typography.labelMedium)
    }


}




@Preview
@Composable
fun TimerTabPreview() {
    TinyTaskTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CounterTabScreen(0)
        }
    }
}