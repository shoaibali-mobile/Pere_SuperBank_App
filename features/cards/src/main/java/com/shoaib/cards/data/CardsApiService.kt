package com.shoaib.cards.data

import com.shoaib.cards.model.CreditCardsResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface CardsApiService {

    @GET("api/cards/credit")
    suspend fun getCreditCards(
        @Header("Authorization") token: String
    ): CreditCardsResponse
}