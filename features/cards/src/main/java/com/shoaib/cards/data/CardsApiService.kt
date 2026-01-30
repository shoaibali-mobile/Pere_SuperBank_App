package com.shoaib.cards.data

import com.shoaib.cards.model.CreditCardsData
import com.shoaib.cards.model.managelimit.CardLimitData
import com.shoaib.cards.model.managelimit.CardLimitsRequest
import com.shoaib.cards.model.addon.RequestAddOnCardData
import com.shoaib.cards.model.addon.RequestAddOnCardRequest
import com.shoaib.cards.model.autopay.SetAutopayData
import com.shoaib.cards.model.autopay.SetAutopayRequest
import com.shoaib.cards.model.debit.DebitCardsData
import com.shoaib.cards.model.setPin.SetPinRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CardsApiService {

    @GET("api/cards/credit")
    suspend fun getCreditCards(
        @Header("Authorization") token: String
    ): ApiResponse<CreditCardsData>

    @GET("api/cards/{cardId}/limits")
    suspend fun getCardLimits(
        @Header("Authorization") token: String,
        @Path("cardId") cardId: String
    ): ApiResponse<CardLimitData>

    @PUT("api/cards/credit/{cardId}/limits")
    suspend fun updateCardLimits(
        @Header("Authorization") token: String,
        @Path("cardId") cardId: String,
        @Body limits: CardLimitsRequest
    ): ApiResponse<CardLimitData>


    @POST("api/cards/credit/{cardId}/pin")
    suspend fun setResetPin(
        @Header("Authorization") token: String,
        @Path("cardId") cardId: String,
        @Body request: SetPinRequest
    ): ApiResponse<Unit>

    @POST("api/cards/credit/{cardId}/autopay")
    suspend fun setAutopay(
        @Header("Authorization") token: String,
        @Path("cardId") cardId: String,
        @Body request: SetAutopayRequest
    ): ApiResponse<SetAutopayData>

    @POST("api/cards/credit/{cardId}/addon")
    suspend fun requestAddOnCard(
        @Header("Authorization") token: String,
        @Path("cardId") cardId: String,
        @Body request: RequestAddOnCardRequest
    ): ApiResponse<RequestAddOnCardData>


    @GET("api/cards/debit")
    suspend fun getDebitCards(
        @Header("Authorization") token: String
    ): ApiResponse<DebitCardsData>
}