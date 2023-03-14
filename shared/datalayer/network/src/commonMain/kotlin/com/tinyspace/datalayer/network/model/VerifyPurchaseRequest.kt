package com.tinyspace.datalayer.network.model

import kotlinx.serialization.Serializable


@Serializable
data class VerifyPurchaseRequest(
    val orderId: String,
    val purchaseId: String,
    val purchaseToken: String,
    val purchaseTime: Long
)