package entities

import logic.enums.ItemCategory

data class AICreateHelp(
    val title: String,
    val description: String,
    val category: ItemCategory
)
