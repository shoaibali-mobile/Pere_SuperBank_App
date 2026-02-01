package com.shoaib.cards.data.credit.repository

import com.shoaib.api.AuthRepository
import com.shoaib.cards.data.CardResult
import com.shoaib.cards.utils.CardsLogger
import com.shoaib.cards.data.CardsApiService
import com.shoaib.cards.model.CreditCardDto
import com.shoaib.cards.model.addon.RequestAddOnCardData
import com.shoaib.cards.model.addon.RequestAddOnCardRequest
import com.shoaib.cards.model.autopay.SetAutopayData
import com.shoaib.cards.model.autopay.SetAutopayRequest
import com.shoaib.cards.model.managelimit.CardLimitData
import com.shoaib.cards.model.managelimit.CardLimitsRequest
import com.shoaib.cards.model.setPin.SetPinRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditCardsRepositoryImpl @Inject constructor(
    private val apiService: CardsApiService,
    private val authRepository: AuthRepository
) : CreditCardsRepository {

    // Single Source of Truth: Cache all credit cards in memory
    private val cardsCache = MutableStateFlow<List<CreditCardDto>>(emptyList())

    override fun getCardsStream(): Flow<List<CreditCardDto>> = cardsCache

    override fun getCardById(id: String): Flow<CreditCardDto?> {
        return cardsCache.map { cards ->
            cards.firstOrNull { it.id == id }
        }
    }

    override suspend fun refreshCards(): CardResult<Unit> {
        return try {
            CardsLogger.d("CreditRepo", "refreshCards: getting token")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken =
                tokens?.accessToken ?: run {
                    CardsLogger.w("CreditRepo", "refreshCards: token null, user not authenticated")
                    return CardResult.Error("User is not authenticated")
                }
            CardsLogger.logTokenState("CreditRepo.refreshCards", true, accessToken.length)

            val response = apiService.getCreditCards("Bearer $accessToken")
            if (response.success && response.data != null) {
                cardsCache.value = response.data.cards
                CardsLogger.d("CreditRepo", "refreshCards: success, cards=${response.data.cards.size}")
                CardResult.Success(Unit)
            } else {
                CardsLogger.e("CreditRepo", "refreshCards: failed - ${response.message}")
                CardResult.Error(response.message ?: "Failed to fetch cards")
            }
        } catch (e: Exception) {
            val code = (e as? retrofit2.HttpException)?.code()
            CardsLogger.e("CreditRepo", "refreshCards: exception code=$code", e)
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
            CardsLogger.d("CreditRepo", "getCardLimits: cardId=$cardId")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken = tokens?.accessToken ?: run {
                CardsLogger.w("CreditRepo", "getCardLimits: token null")
                return CardResult.Error("User is not authenticated")
            }
            CardsLogger.logTokenState("CreditRepo.getCardLimits", true, accessToken.length)

            val response = apiService.getCardLimits("Bearer $accessToken", cardId)
            if (response.success && response.data != null) {
                CardsLogger.d("CreditRepo", "getCardLimits: success")
                CardResult.Success(response.data)
            } else {
                CardsLogger.e("CreditRepo", "getCardLimits: failed - ${response.message}")
                CardResult.Error(response.message ?: "Failed to fetch limits")
            }
        } catch (e: Exception) {
            val code = (e as? retrofit2.HttpException)?.code()
            CardsLogger.e("CreditRepo", "getCardLimits: exception code=$code (401=unauthorized)", e)
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
            CardsLogger.d("CreditRepo", "updateCardLimits: cardId=$cardId")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken = tokens?.accessToken ?: run {
                CardsLogger.w("CreditRepo", "updateCardLimits: token null")
                return CardResult.Error("User is not authenticated")
            }
            CardsLogger.logTokenState("CreditRepo.updateCardLimits", true, accessToken.length)

            val request = CardLimitsRequest(
                domesticLimits = limits.domesticLimits,
                internationalLimits = limits.internationalLimits
            )
            val response = apiService.updateCardLimits("Bearer $accessToken", cardId, request)
            if (response.success && response.data != null) {
                CardsLogger.d("CreditRepo", "updateCardLimits: success")
                CardResult.Success(response.data)
            } else {
                CardsLogger.e("CreditRepo", "updateCardLimits: failed - ${response.message}")
                CardResult.Error(response.message ?: "Failed to update limits")
            }
        } catch (e: Exception) {
            val code = (e as? retrofit2.HttpException)?.code()
            CardsLogger.e("CreditRepo", "updateCardLimits: exception code=$code", e)
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }

    override suspend fun setResetPin(
        cardId: String,
        newPin: String,
        confirmPin: String,
        termsAccepted: Boolean
    ): CardResult<Unit> {
        return try {
            CardsLogger.d("CreditRepo", "setResetPin: cardId=$cardId")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken = tokens?.accessToken ?: run {
                CardsLogger.w("CreditRepo", "setResetPin: token null")
                return CardResult.Error("User is not authenticated")
            }
            CardsLogger.logTokenState("CreditRepo.setResetPin", true, accessToken.length)

            val request = SetPinRequest(
                newPIN = newPin,
                confirmPin = confirmPin,
                termsAccepted = termsAccepted
            )
            val response = apiService.setResetPin("Bearer $accessToken", cardId, request)
            if (response.success) {
                CardsLogger.d("CreditRepo", "setResetPin: success")
                CardResult.Success(Unit)
            } else {
                CardsLogger.e("CreditRepo", "setResetPin: failed - ${response.message}")
                CardResult.Error(response.message ?: "Failed to set PIN")
            }
        } catch (e: Exception) {
            val code = (e as? retrofit2.HttpException)?.code()
            CardsLogger.e("CreditRepo", "setResetPin: exception code=$code (401=unauthorized)", e)
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }

    override suspend fun setAutopay(
        cardId: String,
        amountOption: String,
        linkedAccountId: String,
        autoPayEnabled: Boolean
    ): CardResult<SetAutopayData> {
        return try {
            CardsLogger.d("CreditRepo", "setAutopay: cardId=$cardId")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken = tokens?.accessToken ?: run {
                CardsLogger.w("CreditRepo", "setAutopay: token null")
                return CardResult.Error("User is not authenticated")
            }
            CardsLogger.logTokenState("CreditRepo.setAutopay", true, accessToken.length)
            val request = SetAutopayRequest(
                amountOption = amountOption,
                linkedAccountId = linkedAccountId,
                autoPayEnabled = autoPayEnabled
            )
            val response = apiService.setAutopay("Bearer $accessToken", cardId, request)
            if (response.success && response.data != null) {
                CardsLogger.d("CreditRepo", "setAutopay: success")
                CardResult.Success(response.data)
            } else {
                CardsLogger.e("CreditRepo", "setAutopay: failed - ${response.message}")
                CardResult.Error(response.message ?: "Failed to set autopay")
            }
        } catch (e: Exception) {
            val code = (e as? retrofit2.HttpException)?.code()
            CardsLogger.e("CreditRepo", "setAutopay: exception code=$code", e)
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }

    override suspend fun requestAddOnCard(
        cardId: String,
        customerID: String,
        nameOnCard: String,
        dateOfBirth: String,
        relationship: String
    ): CardResult<RequestAddOnCardData> {
        return try {
            CardsLogger.d("CreditRepo", "requestAddOnCard: cardId=$cardId")
            val tokens = authRepository.getAuthTokens().first()
            val accessToken = tokens?.accessToken ?: run {
                CardsLogger.w("CreditRepo", "requestAddOnCard: token null")
                return CardResult.Error("User is not authenticated")
            }
            CardsLogger.logTokenState("CreditRepo.requestAddOnCard", true, accessToken.length)
            val request = RequestAddOnCardRequest(
                customerID = customerID,
                nameOnCard = nameOnCard,
                dateOfBirth = dateOfBirth,
                relationship = relationship
            )
            val response = apiService.requestAddOnCard("Bearer $accessToken", cardId, request)
            if (response.success && response.data != null) {
                CardsLogger.d("CreditRepo", "requestAddOnCard: success")
                CardResult.Success(response.data)
            } else {
                CardsLogger.e("CreditRepo", "requestAddOnCard: failed - ${response.message}")
                CardResult.Error(response.message ?: "Failed to submit add-on card request")
            }
        } catch (e: Exception) {
            val code = (e as? retrofit2.HttpException)?.code()
            CardsLogger.e("CreditRepo", "requestAddOnCard: exception code=$code", e)
            val errorMessage = when (e) {
                is retrofit2.HttpException -> "Server error: ${e.code()}"
                is java.io.IOException -> "Network error. Please check your connection."
                else -> e.message ?: "Unknown error occurred"
            }
            CardResult.Error(errorMessage, e)
        }
    }
}
