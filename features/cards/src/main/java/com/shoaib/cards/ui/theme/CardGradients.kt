package com.shoaib.cards.ui.theme

import androidx.compose.ui.graphics.Color

object CardGradients {

    // Bruce Wayne / Dark Persona Palette
    private val BatmobileBlack = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364)) // Deep Teal/Black
    private val DarkKnightArmour = listOf(Color(0xFF232526), Color(0xFF414345)) // Gunmetal Grey
    private val GothamNight = listOf(Color(0xFF141E30), Color(0xFF243B55)) // Midnight Blue
    private val WayneManorWood = listOf(Color(0xFF3E5151), Color(0xFFDECBA4)) // Muted Olive/Gold (Sophisticated)
    private val StealthTech = listOf(Color(0xFF000000), Color(0xFF434343)) // Pure Black fade
    private val JokerCard = listOf(Color(0xFF200122), Color(0xFF6f0000)) // Deep Purple/Red (Villain contrast)
    private val LuciusFoxTech = listOf(Color(0xFF29323c), Color(0xFF485563)) // Tech Grey

    val gradients = listOf(
        BatmobileBlack,
        DarkKnightArmour,
        GothamNight,
        StealthTech,
        LuciusFoxTech,
        WayneManorWood,
        JokerCard
    )

    fun getGradient(index: Int): List<Color> {
        return gradients[index % gradients.size]
    }
}
