@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.tinytask.android.ui.stat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun StatScreen(){
    Scaffold {
        Box(modifier = Modifier.padding(it)){
            Text("Coming Soon")
        }
    }
}