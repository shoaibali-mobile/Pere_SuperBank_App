package com.shoaib.cards.model

/**
 * Card Type Enum - Represents different types of payment cards
 */
enum class CardType(val title: String) {
    CreditCards("Credit Cards"),
    DebitCards("Debit Cards"),
    VirtualCards("Virtual Cards"),
    CardSettings("Card Settings")
}
