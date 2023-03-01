@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.android.stat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tinyspace.compose.EnterAnimation
import com.tinyspace.compose.TinyTaskTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun StatScreen(
    viewModel: StatViewModel = koinViewModel(),
    onNavigateToHistoryPage: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EnterAnimation {
        Scaffold { padding ->
            Box(
                modifier = Modifier.padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    Row {
                        ElevatedCard(
                            Modifier
                                .weight(1f)
                                .height(132.dp)
                                .padding(8.dp)
                                .clickable {
                                    onNavigateToHistoryPage()
                                }

                        ) {
                            Box(
                                Modifier.padding(8.dp)
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(R.drawable.icon_stopwatch),
                                            "completed task"
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            stringResource(id = R.string.task_completed),
                                            style =
                                            MaterialTheme.typography.labelLarge
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        "${uiState.finished}", style =
                                        MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                            }
                        }

                        ElevatedCard(
                            Modifier
                                .weight(1f)
                                .height(132.dp)
                                .padding(8.dp)
                        ) {
                            Box(
                                Modifier.padding(8.dp)
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(R.drawable.icon_stopwatch),
                                            "completed task"
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            stringResource(id = R.string.time_duration),
                                            style =
                                            MaterialTheme.typography.labelLarge
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    DurationText(
                                        uiState.totalDuration.hour,
                                        uiState.totalDuration.minute,
                                    )
                                }
                            }
                        }

                    }

                    ElevatedCard(
                        Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(
                                8.dp
                            )
                    ) {

                        Box(contentAlignment = Alignment.Center) {
                            Text(stringResource(id = R.string.donation))
                        }
                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun StatScreenPreview() {
    TinyTaskTheme {
        StatScreen {}
    }
}
