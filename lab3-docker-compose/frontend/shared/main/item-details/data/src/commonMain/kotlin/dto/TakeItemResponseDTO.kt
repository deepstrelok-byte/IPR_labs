package dto

import entities.TakeItemResponse
import kotlinx.serialization.Serializable

@Serializable
data class TakeItemResponseDTO(
    val telegram: String
) {
    fun toDomain() = TakeItemResponse(
        telegram = telegram
    )
}
