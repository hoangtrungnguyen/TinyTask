package com.tinyspace.payment

import android.content.Context
import com.android.billingclient.api.BillingClient

class PaymentBuilder(private val context: Context) {

    fun get(): BillingClient.Builder {
        return BillingClient.newBuilder(context)
            .enablePendingPurchases()
    }
}