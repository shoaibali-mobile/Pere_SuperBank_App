package com.shoaib.cards.data.repository

import com.shoaib.cards.data.CardResult
import com.shoaib.cards.model.CreditCardDto
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    suspend fun refreshCards(): CardResult<Unit> // Fetches from API & updates cache
    fun getCardsStream(): Flow<List<CreditCardDto>> // UI observes this
    fun getCardById(id: String): Flow<CreditCardDto?> // Detail UI observes this
}