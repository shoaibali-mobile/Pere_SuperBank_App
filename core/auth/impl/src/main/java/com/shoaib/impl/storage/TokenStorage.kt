package com.shoaib.pere_super_app_bank.core.auth.impl.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.shoaib.api.model.AuthTokens
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.security.GeneralSecurityException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Encrypted storage for authentication tokens using Android Keystore.
 * 
 * 🔐 SECURITY FEATURES:
 * - Uses Android Keystore automatically (via MasterKey API)
 * - Keys stored in hardware-backed keystore (if device supports it)
 * - Data encrypted with AES-256-GCM
 * - Protects against rooted device attacks
 * 
 * 💣 KEYSTORE INVALIDATION HANDLING:
 * Android Keystore keys can become invalid when:
 * - User removes/changes screen lock (PIN/Fingerprint)
 * - User clears "All Data" in System Settings
 * - Hardware-backed security detects tampering
 * 
 * When this happens, we FORCE LOGOUT by clearing all corrupted data.
 * This ensures the user can log in fresh instead of being stuck.
 * 
 * HOW IT WORKS:
 * 1. MasterKey.Builder creates a key in Android Keystore
 * 2. EncryptedSharedPreferences uses this key to encrypt/decrypt data
 * 3. All data is encrypted before writing to disk
 * 4. Data is decrypted automatically when reading
 * 5. If decryption fails → Clear all data → Force logout
 * 
 * ANDROID KEYSTORE BENEFITS:
 * - Keys never leave the device's secure hardware
 * - Even if device is rooted, keys are protected
 * - Hardware-backed keys can't be extracted
 */
@Singleton
class TokenStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Create MasterKey - automatically uses Android Keystore
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    // Create EncryptedSharedPreferences using the MasterKey
    private val encryptedPrefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "encrypted_auth_tokens",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    // StateFlow for reactive updates
    private val _tokensFlow = MutableStateFlow<AuthTokens?>(null)
    
    init {
        // Load tokens on initialization
        loadTokens()
    }
    
    private fun loadTokens() {
        try {
            val accessToken = encryptedPrefs.getString("access_token", null) ?: return
            val refreshToken = encryptedPrefs.getString("refresh_token", null) ?: return
            val tokenType = encryptedPrefs.getString("token_type", "Bearer") ?: "Bearer"
            val expiresIn = encryptedPrefs.getLong("expires_in", -1)
            val issuedAt = encryptedPrefs.getLong("issued_at", System.currentTimeMillis())
            
            if (expiresIn != -1L) {
                val tokens = AuthTokens(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    tokenType = tokenType,
                    expiresIn = expiresIn,
                    issuedAt = issuedAt
                )
                _tokensFlow.value = tokens
            }
        } catch (e: GeneralSecurityException) {
            // Keystore key invalidated (screen lock changed, device reset, etc.)
            // Clear corrupted data and force logout
            clearAllData()
        } catch (e: Exception) {
            // Other decryption errors - data is corrupted
            // Clear corrupted data and force logout
            clearAllData()
        }
    }

    /**
     * Save tokens to encrypted storage
     * Uses Android Keystore for encryption
     */
    suspend fun saveTokens(tokens: AuthTokens) = withContext(Dispatchers.IO) {
        try {
            encryptedPrefs.edit()
                .putString("access_token", tokens.accessToken)
                .putString("refresh_token", tokens.refreshToken)
                .putString("token_type", tokens.tokenType)
                .putLong("expires_in", tokens.expiresIn)
                .putLong("issued_at", tokens.issuedAt)
                .apply()
            
            // Update Flow
            _tokensFlow.value = tokens
        } catch (e: GeneralSecurityException) {
            // Keystore key invalidated - clear corrupted data
            clearAllData()
            throw e
        } catch (e: Exception) {
            // Other encryption errors
            throw e
        }
    }

    /**
     * Get tokens from encrypted storage
     * Automatically decrypts using Android Keystore
     */
    suspend fun getTokens(): AuthTokens? = withContext(Dispatchers.IO) {
        try {
            val accessToken = encryptedPrefs.getString("access_token", null) ?: return@withContext null
            val refreshToken = encryptedPrefs.getString("refresh_token", null) ?: return@withContext null
            val tokenType = encryptedPrefs.getString("token_type", "Bearer") ?: "Bearer"
            val expiresIn = encryptedPrefs.getLong("expires_in", -1)
            val issuedAt = encryptedPrefs.getLong("issued_at", System.currentTimeMillis())
            
            if (expiresIn == -1L) return@withContext null
            
            AuthTokens(
                accessToken = accessToken,
                refreshToken = refreshToken,
                tokenType = tokenType,
                expiresIn = expiresIn,
                issuedAt = issuedAt
            )
        } catch (e: GeneralSecurityException) {
            // Keystore key invalidated - clear corrupted data and force logout
            clearAllData()
            null
        } catch (e: Exception) {
            // Other decryption errors - clear corrupted data
            clearAllData()
            null
        }
    }

    /**
     * Get tokens as Flow (reactive)
     * Updates automatically when tokens change
     */
    fun getTokensFlow(): Flow<AuthTokens?> {
        return _tokensFlow.asStateFlow()
    }

    /**
     * Clear all tokens from encrypted storage
     */
    suspend fun clearTokens() = withContext(Dispatchers.IO) {
        clearAllData()
    }
    
    /**
     * Clear all data from encrypted storage.
     * Called when Keystore is invalidated or data is corrupted.
     * This forces a logout so user can start fresh.
     */
    private fun clearAllData() {
        try {
            encryptedPrefs.edit().clear().apply()
            _tokensFlow.value = null
        } catch (e: Exception) {
            // Even if clearing fails, reset the Flow
            // This ensures UI shows login screen
            _tokensFlow.value = null
        }
    }
}