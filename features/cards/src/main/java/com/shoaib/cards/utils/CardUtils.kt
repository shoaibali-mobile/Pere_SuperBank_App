package com.shoaib.cards.utils

import com.shoaib.cards.R

/**
 * Utility functions for Card features
 */
object CardUtils {

    /**
     * Get the card network logo resource ID based on the network string.
     * Checks for Visa, Mastercard, Amex, and RuPay (case-insensitive).
     * Defaults to RuPay if no match is found.
     *
     * @param cardNetwork The card network name (e.g. "HDFC VISA", "SBI RUPAY")
     * @return Drawable resource ID
     */
    fun getCardLogo(cardNetwork: String): Int {
        val network = cardNetwork.lowercase()
        return when {
            network.contains("visa") -> R.drawable.visa
            network.contains("mastercard") -> R.drawable.mastercard
            network.contains("amex") || network.contains("american") -> R.drawable.amexlogo
            network.contains("rupay") -> R.drawable.rupay
            else -> R.drawable.rupay
        }
    }
}
