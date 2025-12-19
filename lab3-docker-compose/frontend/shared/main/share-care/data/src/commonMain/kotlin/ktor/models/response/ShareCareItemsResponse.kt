package ktor.models.response

import dto.ItemResponseDTO
import dto.toDomain
import entities.ShareCareItems
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShareCareItemsResponse(
    val responses: List<ItemResponseDTO>,
    @SerialName("my_published_items") val myPublishedItems: List<ItemResponseDTO>
) {
    fun toDomain() = ShareCareItems(
        responses = responses.map { it.toDomain() },
        myPublishedItems = myPublishedItems.map { it.toDomain() }
    )
}