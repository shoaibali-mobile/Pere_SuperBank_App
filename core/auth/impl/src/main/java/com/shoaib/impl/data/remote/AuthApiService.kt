package com.shoaib.impl.data.remote

import com.shoaib.impl.data.remote.model.LoginRequest
import com.shoaib.impl.data.remote.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit API definition for Authentication.
 * Defined inside the implementation module (Data Layer).
 */
interface AuthApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}
