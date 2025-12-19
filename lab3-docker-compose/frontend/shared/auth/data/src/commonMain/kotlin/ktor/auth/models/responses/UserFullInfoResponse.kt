package ktor.auth.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserFullInfoResponse(
    val name: String,
    val phone: String,
    @SerialName("telegram_username") val telegram: String,
    @SerialName("is_verified") val isVerified: Boolean,
    @SerialName("organization_name") val organizationName: String?
)