package com.shoaib.cards.model

import com.google.gson.annotations.SerializedName

data class CreditCardsData(
    @SerializedName("cards") val cards:List<CreditCardDto>
)

// The actual Credit Card object
data class CreditCardDto(
    @SerializedName("id") val id: String,
    @SerializedName("cardNumber") val cardNumber: String,
    @SerializedName("cardType") val cardType: String,
    @SerializedName("cardholderName") val cardHolderName: String,
    @SerializedName("cvv") val cvv: String,
    @SerializedName("expiryMonth") val expiryMonth: Int,
    @SerializedName("expiryYear") val expiryYear: Int,
    @SerializedName("rewardsPoints") val rewardsPoints: Int
)