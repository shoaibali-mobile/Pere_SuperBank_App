package com.shoaib.api

import com.shoaib.api.model.AuthError
import com.shoaib.api.model.AuthTokens
import com.shoaib.api.model.AuthUser
import com.shoaib.api.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for authentication operations.
 * 
 * This interface defines the contract for authentication operations.
 * Feature modules only depend on this interface, not the implementation.
 * 
 * The implementation (Mock or Real) is provided by the :core:auth:impl module
 * and injected via Hilt in the :app module.
 */
interface AuthRepository {
    // === PHASE 1: Core Authentication (MUST HAVE) ===

    /**
     * Login with email and password.
     * Scenarios handled:
     * - Invalid credentials
     * - Account locked (too many failed attempts)
     * - Account suspended
     * - Network errors
     * - Token storage after success
     */
    suspend fun login(email: String, password: String): Result<AuthUser>

    /**
     * Logout current user.
     * Scenarios handled:
     * - Clear all tokens and user data
     * - Call logout API (graceful failure if network fails)
     */
    suspend fun logout(): Result<Unit>

    /**
     * Refresh access token using refresh token.
     * Scenarios handled:
     * - Auto-refresh before token expires
     * - Handle refresh token expiry (force re-login)
     * - Network failures during refresh
     */
    suspend fun refreshToken(): Result<AuthTokens>

    /**
     * Validate current token.
     * Scenarios handled:
     * - Check if token is expired
     * - Auto-refresh if near expiry
     * - Return false if refresh fails
     */
    suspend fun validateToken(): Result<Boolean>

    /**
     * Get current authenticated user.
     * Scenarios handled:
     * - Return cached user if available
     * - Return null if not authenticated
     * - Update when user changes
     */
    fun getCurrentUser(): Flow<AuthUser?>

    /**
     * Check if user is authenticated.
     * Scenarios handled:
     * - Check if user exists AND token is valid
     * - Auto-update when token expires
     */
    fun isAuthenticated(): Flow<Boolean>

    /**
     * Get current auth tokens.
     * Scenarios handled:
     * - Return tokens if available and not expired
     * - Return null if no tokens or expired
     */
    fun getAuthTokens(): Flow<AuthTokens?>

    // === PHASE 2: Registration & Password (HIGH PRIORITY) ===

    /**
     * Register new user account.
     * Scenarios handled:
     * - Email already exists
     * - Weak password
     * - Invalid email format
     * - Network errors
     */
    suspend fun register(
        email: String,
        password: String,
        name: String,
        phoneNumber: String?
    ): Result<AuthUser>

    /**
     * Request password reset email.
     * Scenarios handled:
     * - Email not found (don't reveal this for security)
     * - Rate limiting (too many requests)
     * - Network errors
     */
    suspend fun resetPassword(email: String): Result<Unit>

    /**
     * Complete password reset with token.
     * Scenarios handled:
     * - Invalid/expired token
     * - Weak password
     * - Network errors
     */
    suspend fun verifyPasswordResetToken(
        token: String,
        newPassword: String
    ): Result<Unit>

    /**
     * Change password when logged in.
     * Scenarios handled:
     * - Current password incorrect
     * - New password same as current
     * - Weak password
     * - Network errors
     */
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Unit>

    // === PHASE 3: Email Verification (IMPORTANT) ===

    /**
     * Send email verification.
     * Scenarios handled:
     * - Rate limiting
     * - Already verified
     * - Network errors
     */
    suspend fun sendVerificationEmail(): Result<Unit>

    /**
     * Verify email with token.
     * Scenarios handled:
     * - Invalid/expired token
     * - Already verified
     * - Network errors
     */
    suspend fun verifyEmail(token: String): Result<Unit>

    // === Utility ===

    /**
     * Clear all session data.
     * Used internally and for logout.
     */
    suspend fun clearSession(): Result<Unit>
}