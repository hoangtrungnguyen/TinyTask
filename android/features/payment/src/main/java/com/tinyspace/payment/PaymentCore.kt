package com.tinyspace.payment

import android.content.Context
import com.android.billingclient.api.BillingClient

class PaymentBuilder(private val context: Context) {

    fun get(): BillingClient.Builder {
        return BillingClient.newBuilder(context)
            .enablePendingPurchases()
    }
}


fun com.android.billingclient.api.ProductDetails.getFormattedPrice(): String {
    val price = this.let {
        it.subscriptionOfferDetails?.get(0)
            ?.pricingPhases?.pricingPhaseList?.get(0)?.formattedPrice
    }
    return price ?: "Unknown"
}

fun com.android.billingclient.api.ProductDetails.getBillingPeriod(): String {
    return this.subscriptionOfferDetails?.let {
        it[0]
            ?.pricingPhases?.pricingPhaseList?.get(0)?.billingPeriod
            ?.split("")?.subList(1, 4)?.joinToString("")
    } ?: ""
}
