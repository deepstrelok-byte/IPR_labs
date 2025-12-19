package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import logic.enums.DeliveryType
import logic.enums.ItemCategory


@Serializable
data class ItemResponseDTO(
    val title: String,
    val description: String,
    val location: String,
    val category: ItemCategory,
    @SerialName("delivery_types") val deliveryTypes: List<DeliveryType>,
    val id: String,
    @SerialName("owner") val ownerId: String,
    @SerialName("recipient") val recipientId: String?,
    val images: List<String>,
    val telegram: String? = null
)