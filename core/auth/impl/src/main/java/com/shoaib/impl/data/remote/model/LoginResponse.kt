package com.shoaib.impl.data.remote.model

import com.google.gson.annotations.SerializedName
import com.shoaib.api.model.AuthTokens

/**
 * Network Response model for Login.
 */
data class LoginResponse(
    @SerializedName("user")
    val user: NetworkUser,
    
    @SerializedName("tokens")
    val tokens: AuthTokens
)
