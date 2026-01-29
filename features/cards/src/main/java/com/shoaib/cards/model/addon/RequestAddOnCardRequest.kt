package com.shoaib.cards.model.addon

import com.google.gson.annotations.SerializedName

data class RequestAddOnCardRequest(
    @SerializedName("customerID") val customerID: String,
    @SerializedName("nameOnCard") val nameOnCard: String,
    @SerializedName("dateOfBirth") val dateOfBirth: String,
    @SerializedName("relationship") val relationship: String
)
