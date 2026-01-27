package com.shoaib.impl.data.remote.model

import com.google.gson.annotations.SerializedName
import com.shoaib.api.model.AccountStatus
import com.shoaib.api.model.AuthUser

/**
 * Raw User model from the Network API.
 * Fields are nullable to handle missing JSON keys safely.
 */
data class NetworkUser(
    @SerializedName("id")
    val id: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @SerializedName("isEmailVerified")
    val isEmailVerified: Boolean? = false,

    @SerializedName("isPhoneVerified")
    val isPhoneVerified: Boolean? = false,

    @SerializedName("accountStatus")
    val accountStatus: String? = null,

    @SerializedName("profileImageUrl")
    val profileImageUrl: String? = null,

    @SerializedName("createdAt")
    val createdAt: Long? = null,

    @SerializedName("lastLoginAt")
    val lastLoginAt: Long? = null,

    @SerializedName("requiresPasswordChange")
    val requiresPasswordChange: Boolean? = false,

    @SerializedName("mfaEnabled")
    val mfaEnabled: Boolean? = false,

    @SerializedName("preferredLanguage")
    val preferredLanguage: String? = null
) {
    /**
     * Convert Network Model to Domain Model (AuthUser).
     * This handles all null-checks and default values logic.
     */
    fun toAuthUser(): AuthUser {
        return AuthUser(
            id = id,
            email = email,
            name = name,
            phoneNumber = phoneNumber,
            isEmailVerified = isEmailVerified ?: false,
            isPhoneVerified = isPhoneVerified ?: false,
            accountStatus = parseAccountStatus(accountStatus),
            profileImageUrl = profileImageUrl,
            createdAt = createdAt ?: System.currentTimeMillis(),
            lastLoginAt = lastLoginAt,
            requiresPasswordChange = requiresPasswordChange ?: false,
            mfaEnabled = mfaEnabled ?: false,
            preferredLanguage = preferredLanguage ?: "en" // Fixes the crash!
        )
    }

    private fun parseAccountStatus(status: String?): AccountStatus {
        if (status == null) return AccountStatus.ACTIVE
        return try {
            AccountStatus.valueOf(status)
        } catch (e: IllegalArgumentException) {
            AccountStatus.ACTIVE
        }
    }
}
