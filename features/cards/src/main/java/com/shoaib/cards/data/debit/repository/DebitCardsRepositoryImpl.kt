package com.shoaib.cards.data.debit.repository

import com.shoaib.api.AuthRepository
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.CardsApiService
import com.shoaib.cards.model.debit.DebitCardDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DebitCardsRepositoryImpl @Inject constructor(
    private val apiService: CardsApiService,
    private val authRepository: AuthRepository
) : DebitCardsRepository{

    private val debitCardCache = MutableStateFlow<List<DebitCardDto>>(emptyList())

    override fun getDebitCardsStream(): Flow<List<DebitCardDto>> = debitCardCache


    override suspend fun refreshDebitCards(): CardResult<Unit> {
        return try {
            val tokens = authRepository.getAuthTokens().first()
            val accessToken =
                tokens?.accessToken ?: return CardResult.Error("User is not authenticated")

            val response = apiService.getDebitCards("Bearer $accessToken")

            if (response.success && response.data != null) {
                debitCardCache.value = response.data.cards
                CardResult.Success(Unit)
            } else {
                CardResult.Error(response.message ?: "Failed to fetch debit cards")
            }
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }
}