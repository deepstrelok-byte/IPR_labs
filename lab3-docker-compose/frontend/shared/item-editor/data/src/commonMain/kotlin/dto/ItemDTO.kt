package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDTO(
    val title: String,
    val description: String,
    val location: String,
    val category: String,
    @SerialName("delivery_types") val deliveryTypes: List<String>,
    val images: List<ByteArray>,
    @SerialName("request_id") val requestId: String?
)