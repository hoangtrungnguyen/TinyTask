package com.tinyspace.tinytask.android.ui.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.R
import com.tinyspace.tinytask.android.common.TagIcon
import com.tinyspace.tinytask.android.ui.theme.TinyTaskTheme

@ExperimentalMaterial3Api
@Composable
fun TasksTabScreen() {
    Scaffold(
    ){
        Column(Modifier.padding(it)){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp), content = {
                    items(10) {
                        TaskItem()
                    }
                })
        }
    }
}


@Composable
fun TaskItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp),
    colors =  CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    painterResource(id = R.drawable.ic_computer),
                    contentDescription = "",
                    modifier =  Modifier.size(44.dp)
                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(stringResource(R.string.ui_design), style = MaterialTheme.typography.titleMedium)
                    Row{
                    TagIcon(title = "Work", code = 1)
                    TagIcon(title = "Work", code = 1)
                    }
                }
            }


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("00:42:21", style = MaterialTheme.typography.labelSmall)
                IconButton(
                    modifier =  Modifier.size(24.dp),
                    onClick = { /*Start a Ultradian Cycle*/}) {
                    Icon(painterResource(id = R.drawable.ic_play), "")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TasksTabPreview() {
    TinyTaskTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            TasksTabScreen()
        }

    }
}