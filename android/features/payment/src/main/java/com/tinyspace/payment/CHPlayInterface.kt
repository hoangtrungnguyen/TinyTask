package com.tinyspace.payment

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


class CHPlayInterfaceImpl(private val context: Context) {
    fun openActiveSubscription() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            // Set the URI to the active subscriptions screen in the Google Play Store app
            data = Uri.parse("https://play.google.com/store/account/subscriptions")
            // Set the package name of the Google Play Store app to ensure that the intent
            // is handled by the correct app even if the user has multiple app stores installed
            setPackage("com.android.vending")

            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        // Check if there is an app on the device that can handle the intent
        if (intent.resolveActivity(context.packageManager) != null) {
            // Launch the Google Play Store app with the intent
            ContextCompat.startActivity(context, intent, null)

        } else {
            // Handle the case where the Google Play Store app is not installed on the device
            // or the intent cannot be handled by any app
            // For example, you can open the Google Play Store website in a browser instead
            // by setting the URI to "https://play.google.com/store/account/subscriptions"
        }
    }

}