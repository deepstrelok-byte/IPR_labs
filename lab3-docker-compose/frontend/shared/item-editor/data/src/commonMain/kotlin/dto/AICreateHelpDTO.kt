package dto

import kotlinx.serialization.Serializable
import logic.enums.ItemCategory

@Serializable
data class AICreateHelpDTO(
    val name: String,
    val description: String,
    val category: ItemCategory
)
