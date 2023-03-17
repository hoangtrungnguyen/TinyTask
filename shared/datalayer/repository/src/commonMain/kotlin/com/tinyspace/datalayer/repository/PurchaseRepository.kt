package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.network.Network
import com.tinyspace.datalayer.network.model.VerifyPurchaseRequest

class PurchaseRepository(
    private val network: Network = Network
) {
    suspend fun verify(
        purchaseId: String
    ) {
        network.verify(
            VerifyPurchaseRequest(
                "", purchaseId, "", -1
            )
        )
    }
}