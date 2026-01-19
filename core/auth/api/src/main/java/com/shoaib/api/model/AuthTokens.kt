package com.shoaib.api.model

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long, // seconds until expiry
    val issuedAt: Long = System.currentTimeMillis()
){
    /**
     * Calculate when token expires (in milliseconds)
     */
    val expiresAt: Long
        get() = issuedAt + (expiresIn * 1000)

    /**
     * Check if token is expired
     */
    val isExpired: Boolean
        get() = System.currentTimeMillis() >= expiresAt

    /**
     * Check if token is near expiry (within 5 minutes)
     * Used to trigger auto-refresh
     */
    val isNearExpiry: Boolean
        get() = (expiresAt - System.currentTimeMillis()) < (5 * 60 * 1000)
}
