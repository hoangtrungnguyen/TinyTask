package com.tinyspace.payment

import com.android.billingclient.api.ProductDetails
import com.tinyspace.common.Event
import com.tinyspace.common.UiState
import com.tinyspace.common.ViewModelState


data class ListPackageUiState(
    val packages: List<Package>,
    val connected: Boolean,
    val activated: Boolean
) : UiState

class PaymentEvent : Event


data class Package(
    val name: String,
    val price: String,
    val prodDetail: ProductDetails?,
    val offerToken: String,
    val available: Boolean = false
)


data class ListPackageVMState(
    val packages: List<Package>,
    val connected: Boolean,
    val activated: Boolean
) : ViewModelState<ListPackageUiState> {
    override fun toUiState(): ListPackageUiState {
        return ListPackageUiState(packages, connected, activated)
    }
}
