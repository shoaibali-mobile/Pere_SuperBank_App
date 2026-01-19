package com.shoaib.pere_super_app_bank.core.auth.impl.storage

import com.shoaib.api.model.AuthTokens

/**
 * Data Transfer Object (DTO) for token storage.
 * Used internally within the :core:auth:impl module only.
 * 
 * Note: For tokens, we don't need a separate DTO since AuthTokens
 * is already simple enough. However, if you need to store additional
 * metadata or transform tokens in the future, you can create a TokenData class here.
 * 
 * For now, TokenStorage directly uses AuthTokens since it's already
 * a storage-friendly model with simple fields.
 * 
 * 🏗️ FUTURE EXTENSIBILITY:
 * If you need to store token metadata (like device ID, IP address, etc.),
 * you can create a TokenData DTO here similar to UserData.
 */

// Example (commented out for now):
// @Serializable
// internal data class TokenData(
//     val accessToken: String,
//     val refreshToken: String,
//     val tokenType: String = "Bearer",
//     val expiresIn: Long,
//     val issuedAt: Long,
//     val deviceId: String? = null,
//     val ipAddress: String? = null
// )
//
// internal fun TokenData.toAuthTokens(): AuthTokens {
//     return AuthTokens(
//         accessToken = accessToken,
//         refreshToken = refreshToken,
//         tokenType = tokenType,
//         expiresIn = expiresIn,
//         issuedAt = issuedAt
//     )
// }
//
// internal fun AuthTokens.toTokenData(deviceId: String? = null, ipAddress: String? = null): TokenData {
//     return TokenData(
//         accessToken = accessToken,
//         refreshToken = refreshToken,
//         tokenType = tokenType,
//         expiresIn = expiresIn,
//         issuedAt = issuedAt,
//         deviceId = deviceId,
//         ipAddress = ipAddress
//     )
// }
