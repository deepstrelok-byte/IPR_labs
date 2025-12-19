package entity

import logic.enums.DeliveryType
import logic.enums.ItemCategory

data class ItemResponse(
    val title: String,
    val description: String,
    val location: String,
    val category: ItemCategory,
    val deliveryTypes: List<DeliveryType>,
    val id: String,
    val ownerId: String,
    val recipientId: String?,
    val images: List<String>,
    val telegram: String?
)