package ktor.auth.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeBody(
    val phone: String,
    @SerialName("otp") val code: String
)