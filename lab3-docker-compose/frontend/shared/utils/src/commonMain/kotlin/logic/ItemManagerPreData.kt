package logic

import kotlinx.serialization.Serializable
import logic.enums.DeliveryType
import logic.enums.ItemCategory

@Serializable
data class ItemManagerPreData(
    val requestId: String? = null,
    val itemId: String? = null,
    val title: String = "",
    val description: String = "",
    val category: ItemCategory? = null,
    val deliveryTypes: List<DeliveryType> = listOf(),
    val availableDeliveryTypes: List<DeliveryType> = DeliveryType.entries.toList(),
    val location: String = "Москва, м. Сокол",
    val images: List<String> = listOf(),

)