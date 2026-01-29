package com.shoaib.cards.data.repository

import com.shoaib.cards.data.CardResult
import com.shoaib.cards.model.CreditCardDto
import com.shoaib.cards.model.addon.RequestAddOnCardData
import com.shoaib.cards.model.autopay.SetAutopayData
import com.shoaib.cards.model.managelimit.CardLimitData
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    suspend fun refreshCards(): CardResult<Unit> // Fetches from API & updates cache
    fun getCardsStream(): Flow<List<CreditCardDto>> // UI observes this
    fun getCardById(id: String): Flow<CreditCardDto?> // Detail UI observes this

    suspend fun getCardLimits(cardId: String): CardResult<CardLimitData>
    suspend fun updateCardLimits(cardId: String, limits: CardLimitData): CardResult<CardLimitData>

    suspend fun setResetPin(
        cardId: String,
        newPin: String,
        confirmPin: String,
        termsAccepted: Boolean
    ): CardResult<Unit>

    suspend fun setAutopay(
        cardId: String,
        amountOption: String,
        linkedAccountId: String,
        autoPayEnabled: Boolean
    ): CardResult<SetAutopayData>

    suspend fun requestAddOnCard(
        cardId: String,
        customerID: String,
        nameOnCard: String,
        dateOfBirth: String,
        relationship: String
    ): CardResult<RequestAddOnCardData>
}