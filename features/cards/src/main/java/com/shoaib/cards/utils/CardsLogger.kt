package com.shoaib.cards.utils

import android.util.Log

/**
 * Central logger for the Cards module. Use this tag in Logcat to filter all cards-related logs.
 *
 * Logcat filter: `CardsModule` or `tag:CardsModule`
 */
object CardsLogger {

    private const val TAG = "CardsModule"

    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun d(tagSuffix: String, message: String) {
        Log.d(TAG, "[$tagSuffix] $message")
    }

    fun i(message: String) {
        Log.i(TAG, message)
    }

    fun i(tagSuffix: String, message: String) {
        Log.i(TAG, "[$tagSuffix] $message")
    }

    fun w(message: String) {
        Log.w(TAG, message)
    }

    fun w(tagSuffix: String, message: String) {
        Log.w(TAG, "[$tagSuffix] $message")
    }

    fun w(message: String, throwable: Throwable?) {
        Log.w(TAG, message, throwable)
    }

    fun e(message: String) {
        Log.e(TAG, message)
    }

    fun e(tagSuffix: String, message: String) {
        Log.e(TAG, "[$tagSuffix] $message")
    }

    fun e(message: String, throwable: Throwable?) {
        Log.e(TAG, message, throwable)
    }

    fun e(tagSuffix: String, message: String, throwable: Throwable?) {
        Log.e(TAG, "[$tagSuffix] $message", throwable)
    }

    /** Logs token state for debugging auth (never log full token). */
    fun logTokenState(operation: String, hasToken: Boolean, tokenLength: Int?) {
        if (hasToken && tokenLength != null) {
            d(operation, "Token present, length=$tokenLength")
        } else {
            w(operation, "Token missing or null (hasToken=$hasToken)")
        }
    }
}
