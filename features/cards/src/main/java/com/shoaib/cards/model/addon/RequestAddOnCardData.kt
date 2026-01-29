package com.shoaib.cards.model.addon

import com.google.gson.annotations.SerializedName

data class RequestAddOnCardData(
    @SerializedName("estimatedDeliveryDate") val estimatedDeliveryDate: String? = null,
    @SerializedName("requestId") val requestId: String? = null
)
