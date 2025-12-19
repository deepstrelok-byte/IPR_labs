package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import logic.enums.DeliveryType
import logic.enums.ItemCategory

@Serializable
data class SearchRequestDTO(
    val query: String,
    val category: ItemCategory?,
    @SerialName("delivery_types") val deliveryTypes: List<DeliveryType>,
    val offset: Int,
    @SerialName("to_load") val toLoad: Int
    )