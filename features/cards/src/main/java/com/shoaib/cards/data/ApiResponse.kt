package com.shoaib.cards.data

import com.google.gson.annotations.SerializedName

/**
 * Generic API response wrapper for all card APIs.
 * Use ApiResponse<CreditCardsData>, ApiResponse<CardLimitData>, etc.
 */
data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: T? = null,
    @SerializedName("message") val message: String? = null
)
