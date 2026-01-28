package com.shoaib.cards.model.managelimit

import com.google.gson.annotations.SerializedName

data class CardLimitsResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: CardLimitData
)

data class CardLimitsRequest(
    @SerializedName("domesticLimits")
    val domesticLimits: List<LimitItem>,
    @SerializedName("internationalLimits")
    val internationalLimits: List<LimitItem>
)

data class CardLimitData(
    @SerializedName("cardId")
    val cardId: String? = null,
    @SerializedName("domesticLimits")
    val domesticLimits: List<LimitItem>,
    @SerializedName("internationalLimits")
    val internationalLimits: List<LimitItem>
)

data class LimitItem(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("type")
    val type: String,
    @SerializedName("isEnabled")
    val isEnabled: Boolean,
    @SerializedName("currentLimit")
    val currentLimit: Double,
    @SerializedName("maxLimit")
    val maxLimit: Double,
    @SerializedName("canSetLimit")
    val canSetLimit: Boolean
)
