package com.shoaib.cards.data.debit.repository

import com.shoaib.api.AuthRepository
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.data.CardsApiService
import com.shoaib.cards.model.debit.DebitCardDto
import com.shoaib.cards.model.setPin.SetPinRequest
import com.shoaib.cards.utils.CardsLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DebitCardsRepositoryImpl @Inject constructor(
    private val apiService: CardsApiService,
    private val authRepository: AuthRepository
) : DebitCardsRepository {

    private val debitCardCache = MutableStateFlow<List<DebitCardDto>>(emptyList())

    override fun getDebitCardsStream(): Flow<List<DebitCardDto>> = debitCardCache

    override fun getDebitCardById(cardId: String): Flow<DebitCardDto?> =
        debitCardCache.map { list -> list.firstOrNull { it.id == cardId } }

    override suspend fun setResetPin(
        cardId: String,
        newPIN: String,
        confirmPIN: String,
        termsAccepted: Boolean
    ): CardResult<Unit> {
        return try {
            CardsLogger.d("DebitRepo", "setResetPin: cardId=$cardId")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken = tokens?.accessToken ?: run {
                CardsLogger.w("DebitRepo", "setResetPin: token null")
                return CardResult.Error("User is not authenticated")
            }
            CardsLogger.logTokenState("DebitRepo.setResetPin", true, accessToken.length)
            val request = SetPinRequest(
                newPIN = newPIN,
                confirmPin = confirmPIN,
                termsAccepted = termsAccepted
            )
            val response = apiService.setDebitResetPin("Bearer $accessToken", cardId, request)
            if (response.success) {
                CardsLogger.d("DebitRepo", "setResetPin: success")
                CardResult.Success(Unit)
            } else {
                CardsLogger.e("DebitRepo", "setResetPin: failed - ${response.message}")
                CardResult.Error(response.message ?: "Failed to set PIN")
            }
        } catch (e: Exception) {
            val code = (e as? retrofit2.HttpException)?.code()
            CardsLogger.e("DebitRepo", "setResetPin: exception code=$code", e)
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }

    override suspend fun refreshDebitCards(): CardResult<Unit> {
        return try {
            CardsLogger.d("DebitRepo", "refreshDebitCards: getting token")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken =
                tokens?.accessToken ?: run {
                    CardsLogger.w("DebitRepo", "refreshDebitCards: token null")
                    return CardResult.Error("User is not authenticated")
                }
            CardsLogger.logTokenState("DebitRepo.refreshDebitCards", true, accessToken.length)

            val response = apiService.getDebitCards("Bearer $accessToken")
            if (response.success && response.data != null) {
                debitCardCache.value = response.data.cards
                CardsLogger.d("DebitRepo", "refreshDebitCards: success, cards=${response.data.cards.size}")
                CardResult.Success(Unit)
            } else {
                CardsLogger.e("DebitRepo", "refreshDebitCards: failed - ${response.message}")
                CardResult.Error(response.message ?: "Failed to fetch debit cards")
            }
        } catch (e: Exception) {
            val code = (e as? retrofit2.HttpException)?.code()
            CardsLogger.e("DebitRepo", "refreshDebitCards: exception code=$code", e)
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }
}