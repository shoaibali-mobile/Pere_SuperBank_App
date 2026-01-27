package com.shoaib.impl

import com.shoaib.api.AuthRepository
import com.shoaib.api.model.AuthError
import com.shoaib.api.model.AuthTokens
import com.shoaib.api.model.AuthUser
import com.shoaib.api.model.Result
import com.shoaib.impl.data.remote.AuthApiService
import com.shoaib.impl.data.remote.model.LoginRequest
import com.shoaib.pere_super_app_bank.core.auth.impl.storage.TokenStorage
import com.shoaib.pere_super_app_bank.core.auth.impl.storage.UserStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val tokenStorage: TokenStorage,
    private val userStorage: UserStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        android.util.Log.d("LoginDebug", "AuthRepository: login called for $email")
        return try {
            // Map parameters to Request DTO
            val request = LoginRequest(
                userID = email, // Assuming email is used as userID
                password = password
            )

            // Make API Call
            android.util.Log.d("LoginDebug", "AuthRepository: Sending request to API...")
            val response = apiService.login(request)
            android.util.Log.d("LoginDebug", "AuthRepository: Response received: $response")

            // Convert Network User to Domain User (Safe mapping)
            val authUser = response.user.toAuthUser()

            // Save session data
            tokenStorage.saveTokens(response.tokens)
            userStorage.saveUser(authUser)

            android.util.Log.d("LoginDebug", "AuthRepository: Data saved successfully")
            Result.Success(authUser)
        } catch (e: Exception) {
            android.util.Log.e("LoginDebug", "AuthRepository: Exception during login", e)
            // Error handling could be more sophisticated (parsing error body, etc.)
            Result.Failure(AuthError.NetworkError(e.message ?: "Login failed", e))
        }
    }

    override suspend fun logout(): Result<Unit> {
        tokenStorage.clearTokens()
        userStorage.clearUser()
        return Result.Success(Unit)
    }

    override suspend fun refreshToken(): Result<AuthTokens> {
        return Result.Failure(AuthError.NetworkError("Refresh not implemented"))
    }

    override suspend fun validateToken(): Result<Boolean> {
        val tokens = tokenStorage.getTokens()
        return Result.Success(tokens != null && !tokens.isExpired)
    }

    override fun getCurrentUser(): Flow<AuthUser?> {
        return userStorage.getUserFlow()
    }

    override fun isAuthenticated(): Flow<Boolean> {
        // Simple check: if we have tokens, we are authenticated
        return tokenStorage.getTokensFlow().map { tokens ->
            tokens != null && !tokens.isExpired
        }
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
        return Result.Failure(AuthError.NetworkError("Register not implemented"))
    }

    // Pass-through implementations or TODOs for other methods
    override suspend fun resetPassword(email: String): Result<Unit> = Result.Success(Unit)
    override suspend fun verifyPasswordResetToken(token: String, newPassword: String): Result<Unit> = Result.Success(Unit)
    override suspend fun changePassword(currentPassword: String, newPassword: String): Result<Unit> = Result.Success(Unit)
    override suspend fun sendVerificationEmail(): Result<Unit> = Result.Success(Unit)
    override suspend fun verifyEmail(token: String): Result<Unit> = Result.Success(Unit)
    override suspend fun clearSession(): Result<Unit> = logout()
    
    override suspend fun hasPin(): Boolean = false // Implement PIN storage later
    override suspend fun setPin(pin: String): Result<Unit> = Result.Success(Unit)
    override suspend fun verifyPin(pin: String): Result<Boolean> = Result.Success(true)
}
