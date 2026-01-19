package com.shoaib.api.model

/**
 * Sealed class for all authentication errors.
 * Each error type represents a specific scenario.
 * 
 * Uses abstract properties to avoid duplicate backing fields.
 * Children provide the actual property implementations.
 */
sealed class AuthError {
    abstract val message: String
    abstract val code: String?
    abstract val cause: Throwable?
    
    // Network Errors
    data class NetworkError(
        override val message: String = "Network connection failed",
        override val cause: Throwable? = null
    ) : AuthError() {
        override val code: String = "NETWORK_ERROR"
    }

    data class TimeoutError(
        override val message: String = "Request timed out",
        override val cause: Throwable? = null
    ) : AuthError() {
        override val code: String = "TIMEOUT_ERROR"
    }

    // Authentication Errors
    data class InvalidCredentials(
        override val message: String = "Invalid email or password"
    ) : AuthError() {
        override val code: String = "INVALID_CREDENTIALS"
        override val cause: Throwable? = null
    }

    data class AccountLocked(
        override val message: String = "Account is locked. Please try again later.",
        val unlockTime: Long? = null
    ) : AuthError() {
        override val code: String = "ACCOUNT_LOCKED"
        override val cause: Throwable? = null
    }

    data class AccountSuspended(
        override val message: String = "Account has been suspended"
    ) : AuthError() {
        override val code: String = "ACCOUNT_SUSPENDED"
        override val cause: Throwable? = null
    }

    data class EmailNotVerified(
        override val message: String = "Please verify your email address"
    ) : AuthError() {
        override val code: String = "EMAIL_NOT_VERIFIED"
        override val cause: Throwable? = null
    }

    data class TooManyAttempts(
        override val message: String = "Too many login attempts. Please try again later.",
        val retryAfter: Long? = null // seconds
    ) : AuthError() {
        override val code: String = "TOO_MANY_ATTEMPTS"
        override val cause: Throwable? = null
    }

    // Token Errors
    data class TokenExpired(
        override val message: String = "Session expired. Please login again."
    ) : AuthError() {
        override val code: String = "TOKEN_EXPIRED"
        override val cause: Throwable? = null
    }

    data class TokenRefreshFailed(
        override val message: String = "Failed to refresh session"
    ) : AuthError() {
        override val code: String = "TOKEN_REFRESH_FAILED"
        override val cause: Throwable? = null
    }

    // Registration Errors
    data class EmailAlreadyExists(
        override val message: String = "An account with this email already exists"
    ) : AuthError() {
        override val code: String = "EMAIL_EXISTS"
        override val cause: Throwable? = null
    }

    data class WeakPassword(
        override val message: String = "Password does not meet security requirements"
    ) : AuthError() {
        override val code: String = "WEAK_PASSWORD"
        override val cause: Throwable? = null
    }

    data class InvalidEmail(
        override val message: String = "Invalid email format"
    ) : AuthError() {
        override val code: String = "INVALID_EMAIL"
        override val cause: Throwable? = null
    }

    // Server Errors
    data class ServerError(
        override val message: String = "Server error. Please try again later.",
        val statusCode: Int? = null
    ) : AuthError() {
        override val code: String = "SERVER_ERROR"
        override val cause: Throwable? = null
    }

    // Unknown Error
    data class UnknownError(
        override val message: String = "An unexpected error occurred",
        override val cause: Throwable? = null
    ) : AuthError() {
        override val code: String = "UNKNOWN_ERROR"
    }
}
