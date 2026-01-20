package com.shoaib.api.model


sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Failure(val error: AuthError) : Result<Nothing>()

    inline fun onSuccess(action: (value: T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onFailure(action: (error: AuthError) -> Unit): Result<T> {
        if (this is Failure) action(error)
        return this
    }

    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Failure -> null
    }
    

    fun errorOrNull(): AuthError? = when (this) {
        is Success -> null
        is Failure -> error
    }

    fun isSuccess(): Boolean = this is Success
    
    fun isFailure(): Boolean = this is Failure

    /**
     * Returns the value if this is a Success, or the result of calling [defaultValue] if this is a Failure.
     */
    inline fun getOrElse(defaultValue: () -> @UnsafeVariance T): T = when (this) {
        is Success -> data
        is Failure -> defaultValue()
    }
}
