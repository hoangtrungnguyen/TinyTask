package com.tinyspace.payment

import com.android.billingclient.api.ProductDetails
import com.tinyspace.common.Event
import com.tinyspace.common.UiState
import com.tinyspace.common.ViewModelState

data class PaymentVMState(
    val subscriptions: List<Subscription> = emptyList(),
    val connected: Boolean = false,
    val activated: Boolean = false,
    val canceled: Boolean = false,
    val currentSubscription: Subscription?
) : ViewModelState<PaymentUiState> {
    override fun toUiState(): PaymentUiState {
        return if (activated) {
            PaymentUiState.SubscriptionActivated(
                currentSubscription ?: Subscription.empty()
            )
        } else {
            PaymentUiState.ListSubscription(
                subscriptions
            )
        }
    }
}

sealed class PaymentUiState : UiState {
    data class SubscriptionActivated(
        val subscription: Subscription,
    ) : PaymentUiState()

    data class ListSubscription(
        val subscriptions: List<Subscription>
    ) : PaymentUiState()
}

class PaymentEvent : Event


data class Subscription(
    val name: String,
    val price: String,
    val prodDetail: ProductDetails?,
    val offerToken: String
) {
    companion object {
        fun empty() = Subscription(
            "", "", null, ""
        )
    }
}
