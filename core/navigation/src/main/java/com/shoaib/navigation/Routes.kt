package com.shoaib.navigation

import kotlinx.serialization.Serializable

/**
 * Global Navigation Routes
 * 
 * Defined in :core:navigation so that any feature module can reference them
 * without creating circular dependencies.
 */

@Serializable
sealed interface AuthRoute {
    @Serializable
    object Login : AuthRoute
    
    @Serializable
    object Register : AuthRoute

    @Serializable
    object Pin : AuthRoute

}

@Serializable
object HomeRoute

@Serializable
object ProfileRoute

@Serializable
object CardsRoute

@Serializable
object DebitCardsRoute

@Serializable
object CreditCardsRoute

@Serializable
data class CardDetailsRoute(val cardId: String)

@Serializable
data class DebitCardDetailsRoute(val cardId: String)

@Serializable
data class ManageLimitsRoute(val cardId: String)

@Serializable
data class SetResetPinRoute(val cardId: String, val isDebit: Boolean = false)

@Serializable
data class SetAutopayRoute(val cardId: String)

@Serializable
data class RequestAddOnCardRoute(val cardId: String)


