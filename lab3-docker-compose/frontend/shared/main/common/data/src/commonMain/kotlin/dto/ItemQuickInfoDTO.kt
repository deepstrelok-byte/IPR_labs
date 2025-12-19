package dto

import entity.ItemQuickInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import logic.enums.ItemStatus

@Serializable
data class ItemQuickInfoDTO(
    val status: ItemStatus,
    @SerialName("opponent_id") val opponentId: String,
    @SerialName("opponent_name") val opponentName: String,
    @SerialName("opponent_is_verified") val opponentIsVerified: Boolean,
    @SerialName("opponent_organization_name") val opponentOrganizationName: String?,
    @SerialName("opponent_donated") val opponentDonated: Int,
    @SerialName("opponent_received") val opponentReceived: Int
) {
    fun toDomain() = ItemQuickInfo(
        status = status,
        opponentId = opponentId,
        opponentName = opponentName,
        opponentIsVerified = opponentIsVerified,
        opponentOrganizationName = opponentOrganizationName,
        opponentDonated = opponentDonated,
        opponentReceived = opponentReceived
    )
}