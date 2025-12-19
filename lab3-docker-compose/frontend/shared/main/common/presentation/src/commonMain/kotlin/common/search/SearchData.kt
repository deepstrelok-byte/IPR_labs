package common.search

import logic.enums.DeliveryType
import logic.enums.ItemCategory

data class SearchData(
    val category: ItemCategory? = null,
    val deliveryTypes: List<DeliveryType> = listOf(),
    val query: String = ""
)
