@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.payment

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel


@Composable
fun PaymentUi(
    viewModel: PaymentVM = koinViewModel(),
    navigateBack: () -> Boolean
) {

    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_donation_screen)) }
            )
        }
    ) {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize(),
        ) {

            uiState.value.let { state ->
                when (state) {
                    is PaymentUiState.SubscriptionActivated -> {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Your donation period ${state.subscription.name}")
                            Box(Modifier.height(30.dp))
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    stringResource(R.string.thanks_sentence),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }

                            TextButton(onClick = { viewModel.cancelSubscription() }) {
                                Text("Cancel donation")
                            }
                        }

                    }
                    is PaymentUiState.ListSubscription -> {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(modifier = Modifier.height(16.dp))
                            for (e in state.subscriptions) {
                                ElevatedCard(
                                    modifier = Modifier.padding(
                                        vertical = 12.dp,
                                        horizontal = 16.dp
                                    ),
                                    onClick = {
                                        viewModel.subscribe(e) { billingClient, billingFlowParams ->
                                            val billingResult = billingClient.launchBillingFlow(
                                                context as Activity,
                                                billingFlowParams
                                            )
                                        }
                                    }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(vertical = 12.dp, horizontal = 8.dp)
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(e.name)
                                        Text(e.price)
                                    }
                                }
                            }
                        }
                    }
                }
            }


        }
    }

//    LaunchedEffect(uiState.value.activated) {
//        if (uiState.value.activated) {
//            navigateBack()
//        }
//    }
}

@Composable
fun ListSubscription() {

}