package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.PurchaseRepository

class VerifySuccessPurchaseUseCase(
    private val purchaseRepository: PurchaseRepository
) {

    suspend operator fun invoke(purchaseId: String) {
        purchaseRepository.verify(purchaseId)
    }
}