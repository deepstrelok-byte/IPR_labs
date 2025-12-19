import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeResponse(
    val token: String,
    @SerialName("user_id") val userId: String,
    val name: String?,
)