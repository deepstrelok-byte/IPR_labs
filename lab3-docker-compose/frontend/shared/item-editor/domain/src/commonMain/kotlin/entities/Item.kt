package entities

import logic.enums.DeliveryType
import logic.enums.ItemCategory

data class Item(
    val images: List<ByteArray>,
    val title: String,
    val description: String,
    val category: ItemCategory,
    val deliveryTypes: List<DeliveryType>,
    val location: String,
    val requestId: String?
)
