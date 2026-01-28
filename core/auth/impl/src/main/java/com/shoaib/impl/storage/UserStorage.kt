package com.shoaib.pere_super_app_bank.core.auth.impl.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.shoaib.api.model.AuthUser
import com.shoaib.impl.model.UserData
import com.shoaib.impl.model.toAuthUser
import com.shoaib.impl.model.toUserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.security.GeneralSecurityException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Create MasterKey - automatically uses Android Keystore
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val json = Json { ignoreUnknownKeys = true }
    private val _userFlow = MutableStateFlow<AuthUser?>(null)

    // Create EncryptedSharedPreferences using the MasterKey
    private var encryptedPrefs: SharedPreferences

    init {
        encryptedPrefs = try {
            createEncryptedPrefs()
        } catch (e: Exception) {
            // 🚨 Fix for AEADBadTagException / corrupted keys
            // If we can't open it, delete the file and start fresh.
            context.deleteSharedPreferences("encrypted_auth_user")
            createEncryptedPrefs()
        }
        
        // Load user on initialization
        loadUser()
    }

    private fun createEncryptedPrefs(): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            "encrypted_auth_user",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    
    private fun loadUser() {
        try {
            val userJson = encryptedPrefs.getString("user_data", null) ?: return
            val userData = json.decodeFromString<UserData>(userJson)
            _userFlow.value = userData.toAuthUser()
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
     * Save user to encrypted storage
     * Uses Android Keystore for encryption
     */
    suspend fun saveUser(user: AuthUser) = withContext(Dispatchers.IO) {
        try {
            val userJson = json.encodeToString(user.toUserData()) // Clean & Simple
            encryptedPrefs.edit { putString("user_data", userJson) }
            _userFlow.value = user
        } catch (e: Exception) {
            clearAllData()
            throw e
        }
    }
    
    /**
     * Get user from encrypted storage
     * Automatically decrypts using Android Keystore
     */
    suspend fun getUser(): AuthUser? = withContext(Dispatchers.IO) {
        try {
            val userJson = encryptedPrefs.getString("user_data", null) ?: return@withContext null
            val userData = json.decodeFromString<UserData>(userJson)
            userData.toAuthUser()
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
     * Get user as Flow (reactive)
     * Updates automatically when user changes
     */
    fun getUserFlow(): Flow<AuthUser?> {
        return _userFlow.asStateFlow()
    }
    

    suspend fun clearUser() = withContext(Dispatchers.IO) {
        clearAllData()
    }
    

    private fun clearAllData() {
        try {
            encryptedPrefs.edit().clear().apply()
            _userFlow.value = null
        } catch (e: Exception) {
            // Even if clearing fails, reset the Flow
            // This ensures UI shows login screen
            _userFlow.value = null
        }
    }
}
