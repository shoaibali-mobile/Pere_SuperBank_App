package com.shoaib.cards.di

import com.shoaib.cards.data.CardsApiService
import com.shoaib.cards.data.repository.CardsRepository
import com.shoaib.cards.data.repository.CardsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CardsModule {

    @Provides
    @Singleton
    fun provideCardsApiService(retrofit: Retrofit): CardsApiService {
        return retrofit.create(CardsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCardsRepository(impl: CardsRepositoryImpl): CardsRepository {
        return impl
    }
}
