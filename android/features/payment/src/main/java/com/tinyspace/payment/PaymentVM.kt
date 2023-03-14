package com.tinyspace.payment

import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.*
import com.tinyspace.common.BaseViewModel
import com.tinyspace.shared.domain.VerifySuccessPurchaseUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class PaymentVM constructor(
    billingClientBuilder: PaymentBuilder,
    private val verifySuccessPurchaseUseCase: VerifySuccessPurchaseUseCase
) : BaseViewModel<PaymentEvent, ListPackageUiState, ListPackageVMState>() {

    override val initialState: ListPackageVMState
        get() = ListPackageVMState(emptyList(), connected = false, activated = false)

    private val _modelState = MutableStateFlow(initialState)
    override val modelState: StateFlow<ListPackageVMState> = _modelState

    override val uiState: StateFlow<ListPackageUiState>
        get() = modelState.map(ListPackageVMState::toUiState).stateIn(
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
//            getSubscriptionDetails()


            //TODO delete when push to production
            verifySuccessPurchaseUseCase("3456789")
        }
    }

    fun subscribePackage(
        subscriptionPackage: Package, startFlow: (
            billingClient: BillingClient, flowParams: BillingFlowParams
        ) -> Unit
    ) {
        subscriptionPackage.prodDetail?.let {
            runBlocking(Dispatchers.IO) {
                val productDetailsParamsList = listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                        .setProductDetails(subscriptionPackage.prodDetail)
                        // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                        // for a list of offers that are available to the user
                        .setOfferToken(subscriptionPackage.offerToken)
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
                    .setProductId("savvycom_sub_1")
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId("savvycom_sub_2")
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId("savvycom_sub_3")
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )
        ).build()


        viewModelScope.launch {
            val productDetailsResult = withContext(Dispatchers.Default) {
                billingClient.queryProductDetails(queryProductDetailsParams)
            }
            productDetailsResult.productDetailsList?.let {
                _modelState.update { state ->
                    state.copy(
                        packages = it.mapIndexed { index, prod ->
                            val prodDetail = prod.subscriptionOfferDetails
                            val price =
                                prodDetail?.get(0)?.pricingPhases?.pricingPhaseList?.get(0)?.formattedPrice
                            Package(
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

            //verify this purchase has been charged
            val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
            val ackPurchaseResult = viewModelScope.launch {
                billingClient.acknowledgePurchase(acknowledgePurchaseParams.build())
            }

            viewModelScope.launch {
                //TODO call api to check if this subscription is ok
//                verifySuccessPurchaseUseCase(
//                    VerifySuccessPurchaseRequest(
//                        purchaseToken =  purchase.purchaseToken,
//                        orderId = purchase.orderId,
//                        purchaseTime = purchase.purchaseTime
//                    )
//                )
            }
        }
    }

}



