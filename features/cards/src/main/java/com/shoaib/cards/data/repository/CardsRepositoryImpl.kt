package com.shoaib.cards.data.repository

import com.shoaib.api.AuthRepository
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.CardsApiService
import com.shoaib.cards.model.CreditCardDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardsRepositoryImpl @Inject constructor(
    private val apiService: CardsApiService,
    private val authRepository: AuthRepository
) : CardsRepository {

    // Single Source of Truth: Cache all cards in memory
    private val cardsCache = MutableStateFlow<List<CreditCardDto>>(emptyList())

    override fun getCardsStream(): Flow<List<CreditCardDto>> = cardsCache

    override fun getCardById(id: String): Flow<CreditCardDto?> {
        return cardsCache.map { cards ->
            cards.firstOrNull { it.id == id }
        }
    }

    override suspend fun refreshCards(): CardResult<Unit> {
        return try {
            // 1. Get Token
            val tokens = authRepository.getAuthTokens().first()
            val accessToken =
                tokens?.accessToken ?: return CardResult.Error("User is not authenticated")

            // 2. Fetch from API
            val response = apiService.getCreditCards("Bearer $accessToken")

            // 3. Update cache if successful
            if (response.success) {
                cardsCache.value = response.data.cards
                CardResult.Success(Unit)
            } else {
                CardResult.Error("Failed to fetch cards: Success flag is false")
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