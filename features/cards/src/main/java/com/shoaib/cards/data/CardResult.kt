package com.shoaib.cards.data

sealed class CardResult<out T> {
    data class Success<out T>(val data: T) : CardResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : CardResult<Nothing>()
    object Loading : CardResult<Nothing>()
}