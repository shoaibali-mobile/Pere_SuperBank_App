package com.shoaib.impl.model

import com.shoaib.api.model.AccountStatus
import com.shoaib.api.model.AuthUser
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) for user storage.
 * Used internally within the :core:auth:impl module only.
 * 
 * This is a storage model that represents how user data is stored
 * in encrypted SharedPreferences. It's separate from the domain model
 * (AuthUser) to keep storage concerns separate from business logic.
 * 
 * 🏗️ ARCHITECTURE BENEFITS:
 * - Readability: UserStorage focuses only on encryption and Flow logic
 * - Encapsulation: Internal visibility ensures it doesn't leak to other modules
 * - Testing: Easier to unit test mapping logic separately
 */
@Serializable
internal data class UserData(
    val id: String,
    val email: String,
    val name: String,
    val phoneNumber: String?,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
    val accountStatus: String,
    val profileImageUrl: String?,
    val createdAt: Long,
    val lastLoginAt: Long?,
    val requiresPasswordChange: Boolean,
    val mfaEnabled: Boolean,
    val preferredLanguage: String,
    val pin: String? = null
)

/**
 * Mappers to convert between Storage Model (UserData) and Domain Model (AuthUser).
 * Keeps the storage logic separate from the business logic.
 */

/**
 * Convert storage model to domain model.
 */
internal fun UserData.toAuthUser(): AuthUser {
    return AuthUser(
        id = id,
        email = email,
        name = name,
        phoneNumber = phoneNumber,
        isEmailVerified = isEmailVerified,
        isPhoneVerified = isPhoneVerified,
        accountStatus = AccountStatus.valueOf(accountStatus),
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        lastLoginAt = lastLoginAt,
        requiresPasswordChange = requiresPasswordChange,
        mfaEnabled = mfaEnabled,
        preferredLanguage = preferredLanguage
    )
}

/**
 * Convert domain model to storage model.
 */
internal fun AuthUser.toUserData(): UserData {
    return UserData(
        id = id,
        email = email,
        name = name,
        phoneNumber = phoneNumber,
        isEmailVerified = isEmailVerified,
        isPhoneVerified = isPhoneVerified,
        // Safety: Handle backend sending null for accountStatus
        accountStatus = try { accountStatus.name } catch (e: Exception) { AccountStatus.ACTIVE.name },
        profileImageUrl = profileImageUrl,
        createdAt = createdAt,
        lastLoginAt = lastLoginAt,
        requiresPasswordChange = requiresPasswordChange,
        mfaEnabled = mfaEnabled,
        preferredLanguage = preferredLanguage,
        pin = null // PIN is stored separately in TokenStorage
    )
}