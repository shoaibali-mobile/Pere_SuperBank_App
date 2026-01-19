package com.shoaib.api.model

data class AuthUser(
    val id: String,
    val email: String,
    val name: String,
    val phoneNumber: String? = null,
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val accountStatus: AccountStatus = AccountStatus.ACTIVE,
    val profileImageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long? = null,
    val requiresPasswordChange: Boolean = false,
    val mfaEnabled: Boolean = false,
    val preferredLanguage: String = "en"
){
    /**
     * Helper to check if user is fully verified
     */
    val isFullyVerified: Boolean
        get() = isEmailVerified && (phoneNumber == null || isPhoneVerified)

    /**
     * Helper to check if user can access banking features
     */
    val canAccessBanking: Boolean
        get() = accountStatus == AccountStatus.ACTIVE && isFullyVerified
}

enum class AccountStatus {
    ACTIVE,
    SUSPENDED,
    LOCKED,
    PENDING_VERIFICATION,
    CLOSED
}
