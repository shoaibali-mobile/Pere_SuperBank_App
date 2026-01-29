package com.shoaib.cards.model.autopay

import com.google.gson.annotations.SerializedName

data class SetAutopayRequest(
    @SerializedName("amountOption") val amountOption: String,
    @SerializedName("linkedAccountId") val linkedAccountId: String,
    @SerializedName("autoPayEnabled") val autoPayEnabled: Boolean
)
