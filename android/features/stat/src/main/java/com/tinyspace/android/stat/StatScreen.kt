@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.tinyspace.android.stat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entryModelOf
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
//                                .height(132.dp)
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
//                                .height(132.dp)
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

//                    Row {
//                        ElevatedCard {
//                            Text("Week")
//                        }
//                        ElevatedCard {
//                            Text("Month")
//                        }
//                    }
                    StatisticGraph()

                }

            }
        }
    }
}

@Composable
fun StatisticGraph(
    hours: ChartEntryModel = entryModelOf(4.2f, 5f, 12f, 3f, 17f, 0f, 0f)
) {

    ElevatedCard(modifier = Modifier.padding(8.dp)) {
        Box(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
            ProvideChartStyle(
                rememberChartStyle(
                    listOf(
                        MaterialTheme.colorScheme.primary
                    )
                )
            ) {
                Chart(
                    chart = columnChart(),
                    model = hours,
                    startAxis = startAxis(
                        title = "Hour",
                        valueFormatter = { y, _ -> "${y.toInt()}h" }
                    ),
                    bottomAxis = bottomAxis(valueFormatter = bottomAxisValueFormatter),
                )
            }
        }
    }
}

private val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
private val bottomAxisValueFormatter =
    AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _ -> daysOfWeek[x.toInt() % daysOfWeek.size] }


private const val COLOR_1_CODE = 0xffa485e0
private const val PERSISTENT_MARKER_X = 10f

private val color1 = Color(COLOR_1_CODE)
private val chartColors = listOf(color1)


@Composable
@Preview
fun StatisticGraphPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            StatisticGraph()
        }
    }
}


@Preview
@Composable
fun ChartComponentPreview() {
    TinyTaskTheme {

    }
}