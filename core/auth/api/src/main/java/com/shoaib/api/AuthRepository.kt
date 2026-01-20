package com.shoaib.api

import com.shoaib.api.model.AuthError
import com.shoaib.api.model.AuthTokens
import com.shoaib.api.model.AuthUser
import com.shoaib.api.model.Result
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    suspend fun login(email: String, password: String): Result<AuthUser>

    suspend fun logout(): Result<Unit>

    suspend fun refreshToken(): Result<AuthTokens>

    suspend fun validateToken(): Result<Boolean>

    fun getCurrentUser(): Flow<AuthUser?>

    fun isAuthenticated(): Flow<Boolean>

    fun getAuthTokens(): Flow<AuthTokens?>

    suspend fun register(
        email: String,
        password: String,
        name: String,
        phoneNumber: String?
    ): Result<AuthUser>

    suspend fun resetPassword(email: String): Result<Unit>

    suspend fun verifyPasswordResetToken(
        token: String,
        newPassword: String
    ): Result<Unit>


    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Unit>


    suspend fun sendVerificationEmail(): Result<Unit>

    suspend fun verifyEmail(token: String): Result<Unit>

    suspend fun clearSession(): Result<Unit>


    // 2FA / Pin security

    suspend fun hasPin(): Boolean

    suspend fun setPin(pin: String): Result<Unit>

    suspend fun verifyPin(pin: String): Result<Boolean>
}