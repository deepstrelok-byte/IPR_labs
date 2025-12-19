package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import logic.enums.DeliveryType
import logic.enums.ItemCategory

@Serializable
data class RequestResponseDTO(
    val text: String,
    val location: String,
    val category: ItemCategory,
    @SerialName("delivery_types") val deliveryTypes: List<DeliveryType>,
    val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("organization_name") val organizationName: String?
)