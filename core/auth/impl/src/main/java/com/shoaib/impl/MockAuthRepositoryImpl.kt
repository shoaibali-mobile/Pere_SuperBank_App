package com.shoaib.impl

import com.shoaib.api.AuthRepository
import com.shoaib.api.model.AuthError
import com.shoaib.api.model.AuthTokens
import com.shoaib.api.model.AuthUser
import com.shoaib.api.model.Result

import com.shoaib.pere_super_app_bank.core.auth.impl.storage.TokenStorage
import com.shoaib.pere_super_app_bank.core.auth.impl.storage.UserStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAuthRepositoryImpl @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val userStorage: UserStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        delay(1000) // Simulate network delay
        return if (email == "test@bank.com" && password == "123456") {
            val user = AuthUser(
                id = "123",
                email = email,
                name = "John Wick",
                phoneNumber = "+1234567890",
                isEmailVerified = true,
                isPhoneVerified = true
            )
            val tokens = AuthTokens(
                accessToken = "mock_access_token",
                refreshToken = "mock_refresh_token",
                expiresIn = 3600
            )
            tokenStorage.saveTokens(tokens)
            userStorage.saveUser(user)
            Result.Success(user)
        } else {
            TODO()
        }
    }

    override suspend fun logout(): Result<Unit> {
        tokenStorage.clearTokens()
        userStorage.clearUser()
        return Result.Success(Unit)
    }

    override suspend fun refreshToken(): Result<AuthTokens> {
        // Mock implementation
        return Result.Failure(com.shoaib.api.model.AuthError.NetworkError("Not implemented in mock"))
    }

    override suspend fun validateToken(): Result<Boolean> {
        val tokens = tokenStorage.getTokens()
        val isValid = tokens?.accessToken?.isNotBlank() == true
        return Result.Success(isValid)
    }


    override fun getCurrentUser(): Flow<AuthUser?> {
        return userStorage.getUserFlow()
    }

    override fun isAuthenticated(): Flow<Boolean> {
        return flow { emit(true) } // Simplified for mock
    }

    override fun getAuthTokens(): Flow<AuthTokens?> {
        return tokenStorage.getTokensFlow()
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String,
        phoneNumber: String?
    ): Result<AuthUser> {
        return Result.Failure(com.shoaib.api.model.AuthError.ServerError("Registration not supported in mock"))
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun verifyPasswordResetToken(token: String, newPassword: String): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun changePassword(currentPassword: String, newPassword: String): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun sendVerificationEmail(): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun verifyEmail(token: String): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun clearSession(): Result<Unit> {
        return logout()
    }

    override suspend fun hasPin(): Boolean {
        val user = userStorage.getUser()
        return user?.email == "test@bank.com"
    }

    override suspend fun verifyPin(pin: String): Result<Boolean> {
        delay(500)
        return if (pin == "1234") Result.Success(true) else Result.Failure(AuthError.InvalidCredentials("Wrong PIN"))
    }

    override suspend fun setPin(pin: String): Result<Unit> {
        return Result.Success(Unit)
    }

}
