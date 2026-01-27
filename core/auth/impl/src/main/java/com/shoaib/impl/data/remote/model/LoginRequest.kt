package com.shoaib.impl.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Network Request model for Login.
 * Internal to the implementation layer.
 */
data class LoginRequest(
    @SerializedName("userID")
    val userID: String,
    
    @SerializedName("password")
    val password: String
)
