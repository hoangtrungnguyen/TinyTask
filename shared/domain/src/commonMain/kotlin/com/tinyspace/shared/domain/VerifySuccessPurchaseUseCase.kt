package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.PurchaseRepository

class VerifySuccessPurchaseUseCase(
    private val purchaseRepository: PurchaseRepository
) {

    suspend operator fun invoke(purchaseId: String): Boolean {
        purchaseRepository.verify(purchaseId)
        return true
    }
}