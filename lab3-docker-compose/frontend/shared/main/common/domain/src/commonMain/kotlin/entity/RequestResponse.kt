package entity

import logic.enums.DeliveryType
import logic.enums.ItemCategory


data class RequestResponse(
    val text: String,
    val location: String,
    val category: ItemCategory,
    val deliveryTypes: List<DeliveryType>,
    val id: String,
    val userId: String,
    val organizationName: String?
)