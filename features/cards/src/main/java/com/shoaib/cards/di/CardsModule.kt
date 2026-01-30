package com.shoaib.cards.di

import com.shoaib.cards.data.CardsApiService
import com.shoaib.cards.data.credit.repository.CreditCardsRepository
import com.shoaib.cards.data.credit.repository.CreditCardsRepositoryImpl
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
    fun provideCreditCardsRepository(impl: CreditCardsRepositoryImpl): CreditCardsRepository {
        return impl
    }
}
