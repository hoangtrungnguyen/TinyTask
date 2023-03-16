package com.tinyspace.payment

import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.*
import com.tinyspace.common.BaseViewModel
import com.tinyspace.shared.domain.VerifySuccessPurchaseUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.json.JSONObject

class PaymentVM constructor(
    billingClientBuilder: PaymentBuilder,
    private val verifySuccessPurchaseUseCase: VerifySuccessPurchaseUseCase

) : BaseViewModel<PaymentEvent, PaymentUiState, PaymentVMState>() {

    override val initialState: PaymentVMState = PaymentVMState(
        emptyList(),
        connected = false,
        activated = false,
        currentSubscription = null
    )

    private val _modelState = MutableStateFlow(initialState)
    override val modelState: StateFlow<PaymentVMState> = _modelState

    override val uiState: StateFlow<PaymentUiState>
        get() = modelState.map(PaymentVMState::toUiState).stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            initialState.toUiState()
        )

    override fun onEvent(event: PaymentEvent) {
        TODO("Not yet implemented")
    }

    private val billingClient: BillingClient

    private val purchaseListener = PurchasesUpdatedListener { result, purchases ->
        when (result.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                if (purchases == null) return@PurchasesUpdatedListener
                for (purchase in purchases)
                    handlePurchase(purchase)
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {

            }
            else -> {
                // Handle any other error codes.
            }
        }
    }

    init {
        billingClient = billingClientBuilder
            .get()
            .setListener(this.purchaseListener)
            .build()


        startConnection()
        viewModelScope.launch {
            delay(300)
            getCurrentSubscription()
//            TODO delete when push to production
//            verifySuccessPurchaseUseCase("3456789")
        }
    }

    fun subscribePackage(
        subscriptionSubscription: Subscription, startFlow: (
            billingClient: BillingClient, flowParams: BillingFlowParams
        ) -> Unit
    ) {
        subscriptionSubscription.prodDetail?.let {
            runBlocking(Dispatchers.IO) {
                val productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(subscriptionSubscription.prodDetail)
                        .setOfferToken(subscriptionSubscription.offerToken)
                        .build()
                )

                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()

                // Launch the billing flow
                startFlow(billingClient, billingFlowParams)
            }
        }
    }

    private fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                when (billingResult.responseCode) {
                    BillingClient.BillingResponseCode.OK -> {
                        // The BillingClient is ready. You can query purchases here.
                        _modelState.update { state ->
                            state.copy(
                                connected = true
                            )
                        }
                        println(_modelState.value)
                    }
                    BillingClient.BillingResponseCode.USER_CANCELED -> {
                        // Handle an error caused by a user cancelling the purchase flow.
                    }
                    else -> {
                        // Handle any other error codes.
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                startConnection()
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        this.billingClient.endConnection()
    }

    private fun getSubscriptionDetails() {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder().setProductList(
            mutableListOf(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId("month_1")
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId("donation2")
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
            )
        ).build()


        viewModelScope.launch {
            val productDetailsResult = withContext(Dispatchers.Default) {
                billingClient.queryProductDetails(queryProductDetailsParams)
            }
            productDetailsResult.productDetailsList?.let {
                _modelState.update { state ->
                    state.copy(
                        subscriptions = it.mapIndexed { index, prod ->
                            val prodDetail = prod.subscriptionOfferDetails
                            val price =
                                prodDetail?.get(0)?.pricingPhases?.pricingPhaseList?.get(0)?.formattedPrice
                            Subscription(
                                prod.name,
                                price ?: "unknown",
                                prodDetail = it[index],
                                offerToken = prodDetail?.get(0)?.offerToken ?: "",
                            )
                        }
                    )
                }
            }
        }
    }

    private fun handlePurchase(purchase: Purchase?) {
        purchase?.let {
            if (purchase.purchaseState != Purchase.PurchaseState.PURCHASED) return
            if (purchase.isAcknowledged) return

            viewModelScope.launch {
                //TODO call api to check if this subscription is ok
                val isOk = verifySuccessPurchaseUseCase(
//                    VerifySuccessPurchaseRequest(
//                        purchaseToken =  purchase.purchaseToken,
//                        orderId = purchase.orderId,
//                        purchaseTime = purchase.purchaseTime
//                    )
                    "Fake_ID"
                )

                //verify this purchase has been charged
                if (isOk) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams.build())
                    getCurrentSubscription()
                }
            }

        }
    }


    private fun getCurrentSubscription() {
        val params =
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)

        viewModelScope.launch {
            // uses queryPurchasesAsync Kotlin extension function
            val purchasesResult = billingClient.queryPurchasesAsync(
                params
                    .build()
            )

            val purchase = purchasesResult.purchasesList.firstOrNull { it.isAcknowledged }

            if (purchase == null) {
                getSubscriptionDetails()
                return@launch
            }

            val productId = purchase.originalJson.let { JSONObject(it).getString("productId") }

            val productDetailsResult = billingClient.queryProductDetails(
                QueryProductDetailsParams.newBuilder().setProductList(
                    listOf(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(productId)
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()
                    )
                ).build()
            )


            val prodDetail = productDetailsResult.productDetailsList?.firstOrNull()
            val price = prodDetail?.getFormattedPrice()

            _modelState.update {
                it.copy(
                    currentSubscription = Subscription(
                        price = price ?: "unknown",
                        prodDetail = prodDetail,
                        offerToken = "",
                        name = prodDetail?.getBillingPeriod() ?: "unknown period"
                    ),
                    activated = true
                )
            }
        }
    }
}




