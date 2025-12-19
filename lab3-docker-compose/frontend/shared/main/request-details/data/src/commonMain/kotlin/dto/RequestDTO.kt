package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDTO(
    val text: String,
    val location: String,
    val category: String,
    @SerialName("delivery_types") val deliveryTypes: List<String>
)