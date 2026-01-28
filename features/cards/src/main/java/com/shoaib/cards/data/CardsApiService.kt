package com.shoaib.cards.data

import com.shoaib.cards.model.CreditCardsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import com.shoaib.cards.model.managelimit.CardLimitsResponse
import com.shoaib.cards.model.managelimit.CardLimitsRequest


interface CardsApiService {

    @GET("api/cards/credit")
    suspend fun getCreditCards(
        @Header("Authorization") token: String
    ): CreditCardsResponse



    @GET("api/cards/{cardId}/limits")
    suspend fun getCardLimits(
        @Header("Authorization") token: String,
        @Path("cardId") cardId: String
    ): CardLimitsResponse


    @PUT("api/cards/credit/{cardId}/limits")
    suspend fun updateCardLimits(
        @Header("Authorization") token: String,
        @Path("cardId") cardId: String,
        @Body limits: CardLimitsRequest
    ): CardLimitsResponse
}