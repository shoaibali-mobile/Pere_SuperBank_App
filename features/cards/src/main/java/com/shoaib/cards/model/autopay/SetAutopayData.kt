package com.shoaib.cards.model.autopay

import com.google.gson.annotations.SerializedName

data class SetAutopayData(
    @SerializedName("autopayId") val autopayId: String,
    @SerializedName("cardId") val cardId: String,
    @SerializedName("amountOption") val amountOption: String,
    @SerializedName("linkedAccountId") val linkedAccountId: String,
    @SerializedName("autoPayEnabled") val autoPayEnabled: Boolean,
    @SerializedName("activationDate") val activationDate: String? = null
)
