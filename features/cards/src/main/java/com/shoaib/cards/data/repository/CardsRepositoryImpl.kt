package com.shoaib.cards.data.repository

import android.util.Log
import com.shoaib.api.AuthRepository
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.CardsApiService
import com.shoaib.cards.model.CreditCardDto
import com.shoaib.cards.model.managelimit.CardLimitData
import com.shoaib.cards.model.managelimit.CardLimitsRequest
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

    override suspend fun getCardLimits(cardId: String): CardResult<CardLimitData> {
        return try {
            Log.d("CardsRepository", "Fetching limits for cardId: $cardId")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken = tokens?.accessToken ?: return CardResult.Error("User is not authenticated")
            val response = apiService.getCardLimits("Bearer $accessToken", cardId)
            
            if (response.success) {
                Log.d("CardsRepository", "Fetch limits SUCCESS: ${response.data}")
                CardResult.Success(response.data)
            } else {
                Log.e("CardsRepository", "Fetch limits FAILED: success=false")
                CardResult.Error("Failed to fetch limits")
            }
        } catch (e: Exception) {
            Log.e("CardsRepository", "Fetch limits EXCEPTION", e)
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }

    override suspend fun updateCardLimits(cardId: String, limits: CardLimitData): CardResult<CardLimitData> {
        return try {
            Log.d("CardsRepository", "Updating limits for cardId: $cardId")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken = tokens?.accessToken ?: return CardResult.Error("User is not authenticated")
            
            // Map to request model (exclude cardId from body)
            val request = CardLimitsRequest(
                domesticLimits = limits.domesticLimits,
                internationalLimits = limits.internationalLimits
            )
            Log.d("CardsRepository", "Sending PUT request with: $request")
            
            val response = apiService.updateCardLimits("Bearer $accessToken", cardId, request)
            
            if (response.success) {
                Log.d("CardsRepository", "Update limits SUCCESS: ${response.data}")
                CardResult.Success(response.data)
            } else {
                Log.e("CardsRepository", "Update limits FAILED: success=false")
                CardResult.Error("Failed to update limits")
            }
        } catch (e: Exception) {
            Log.e("CardsRepository", "Update limits EXCEPTION", e)
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }
}
