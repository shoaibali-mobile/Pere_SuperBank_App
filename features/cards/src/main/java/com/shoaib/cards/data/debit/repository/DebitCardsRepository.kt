package com.shoaib.cards.data.debit.repository

import com.shoaib.cards.data.CardResult
import com.shoaib.cards.model.debit.DebitCardDto
import kotlinx.coroutines.flow.Flow

interface DebitCardsRepository {
    suspend fun refreshDebitCards(): CardResult<Unit>
    fun getDebitCardsStream(): Flow<List<DebitCardDto>>
}
