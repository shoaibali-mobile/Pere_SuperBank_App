package com.shoaib.cards.model.debit

import com.google.gson.annotations.SerializedName

data class DebitCardsData(
    @SerializedName("cards") val cards: List<DebitCardDto>
)

data class DebitCardDto(
    @SerializedName("id") val id: String,
    @SerializedName("accountNumber") val accountNumber: String,
    @SerializedName("bankName") val bankName: String,
    @SerializedName("cardNumber") val cardNumber: String,
    @SerializedName("cardType") val cardType: String,
    @SerializedName("cardholderName") val cardholderName: String,
    @SerializedName("cvv") val cvv: String,
    @SerializedName("expiryMonth") val expiryMonth: Int,
    @SerializedName("expiryYear") val expiryYear: Int
)
