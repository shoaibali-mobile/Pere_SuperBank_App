package com.shoaib.cards.utils

import com.shoaib.cards.R

/**
 * Masks a card number, showing only the last [visibleLastDigits] digits.
 * Example: "4532123456789012".maskCardNumber(4) -> "************9012"
 */
fun String.maskCardNumber(visibleLastDigits: Int = 4): String {
    val digits = this.filter { it.isDigit() }
    if (digits.length <= visibleLastDigits) return digits
    val last = digits.takeLast(visibleLastDigits)
    return "************$last"
}

/**
 * Masks a card number for PIN/settings: first 6 + ****** + last 4.
 * Example: "6529251234567890".maskCardNumberForPin() -> "652925******7890"
 */
fun String.maskCardNumberForPin(): String {
    val digits = this.filter { it.isDigit() }
    if (digits.length < 11) return maskCardNumber(4)
    return digits.take(6) + "******" + digits.takeLast(4)
}

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
