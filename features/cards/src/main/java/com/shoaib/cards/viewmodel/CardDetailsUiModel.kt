package com.shoaib.cards.viewmodel

data class CardDetailsUiModel(
    val id: String,
    val cardNumber: String,
    val cardHolderName: String,
    val cardType: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val cvv: String,
    val rewardsPoints: Int = 0,
    val isCredit: Boolean,
    val bankName: String? = null,
    val accountNumber: String? = null
)
