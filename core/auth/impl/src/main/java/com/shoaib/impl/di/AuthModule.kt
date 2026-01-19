package com.shoaib.impl.di

import com.shoaib.api.AuthRepository
import com.shoaib.impl.MockAuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: MockAuthRepositoryImpl
    ): AuthRepository
}
