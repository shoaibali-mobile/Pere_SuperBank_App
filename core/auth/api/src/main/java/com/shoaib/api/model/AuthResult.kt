package com.shoaib.api.model

/**
 * Result wrapper for operations that may fail.
 * Used by ViewModels to handle success/failure scenarios.
 * 
 * Example usage:
 * ```
 * when (val result = authRepository.login(email, password)) {
 *     is Result.Success -> // Handle success
 *     is Result.Failure -> // Handle error
 * }
 * ```
 */
sealed class Result<out T> {
    /**
     * Represents a successful operation with data
     */
    data class Success<out T>(val data: T) : Result<T>()
    
    /**
     * Represents a failed operation with error details
     */
    data class Failure(val error: AuthError) : Result<Nothing>()
    
    /**
     * Execute action if result is success
     * 
     * @param action Lambda to execute with the success data
     * @return The same result for chaining
     */
    inline fun onSuccess(action: (value: T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }
    
    /**
     * Execute action if result is failure
     * 
     * @param action Lambda to execute with the error
     * @return The same result for chaining
     */
    inline fun onFailure(action: (error: AuthError) -> Unit): Result<T> {
        if (this is Failure) action(error)
        return this
    }
    
    /**
     * Get the data if success, null otherwise
     */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Failure -> null
    }
    
    /**
     * Get the error if failure, null otherwise
     */
    fun errorOrNull(): AuthError? = when (this) {
        is Success -> null
        is Failure -> error
    }
    
    /**
     * Check if result is success
     */
    fun isSuccess(): Boolean = this is Success
    
    /**
     * Check if result is failure
     */
    fun isFailure(): Boolean = this is Failure
}
