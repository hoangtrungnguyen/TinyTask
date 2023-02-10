@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.tinytask.android.ui.onboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tinyspace.tinytask.android.R
import com.tinyspace.tinytask.android.ui.theme.TinyTaskTheme


@Composable
fun OnBoardingScreen(
    moveToNext: () -> Unit
) {

    AnimatedVisibility(true) {
        Scaffold(
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(id = R.string.onboard))
                    OutlinedButton(onClick = moveToNext) {
                        Text(stringResource(id = R.string.move_to_next))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun OnBoardingScreenPreview() {
    TinyTaskTheme {
        OnBoardingScreen({})
    }

}