package com.tinyspace.tinytask.android

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.theme.TinyTaskTheme

@Composable
fun TasksTab(){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
        ,content = {
        items(10, ){
            Card(modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)){
                    Text("Task 1")
                    Text("Task 2")
                }
            }
        }
    })

}

@Preview
@Composable
fun TasksTabPreview(){
    TinyTaskTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background){
            TasksTab()
        }

    }
}