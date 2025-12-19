package entities

import logic.enums.DeliveryType
import logic.enums.ItemCategory

data class Request(
    val text: String,
    val category: ItemCategory,
    val deliveryTypes: List<DeliveryType>,
    val location: String
)
