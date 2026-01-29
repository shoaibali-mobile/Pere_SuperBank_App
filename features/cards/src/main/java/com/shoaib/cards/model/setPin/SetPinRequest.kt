package com.shoaib.cards.model.setPin

import com.google.gson.annotations.SerializedName

data class SetPinRequest(
    @SerializedName("newPIN") val newPIN: String,
    @SerializedName("confirmPIN") val confirmPin: String,
    @SerializedName("termsAccepted") val termsAccepted: Boolean
)
