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
object  CreditCardRoute
