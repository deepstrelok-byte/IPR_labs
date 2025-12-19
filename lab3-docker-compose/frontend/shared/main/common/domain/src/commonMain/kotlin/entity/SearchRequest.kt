package entity

import logic.enums.DeliveryType
import logic.enums.ItemCategory

data class SearchRequest(
    val query: String,
    val category: ItemCategory?,
    val deliveryTypes: List<DeliveryType>,
    val offset: Int,
    val toLoad: Int
)
