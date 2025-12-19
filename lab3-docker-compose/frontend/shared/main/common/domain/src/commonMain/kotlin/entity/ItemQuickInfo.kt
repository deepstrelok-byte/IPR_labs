package entity

import logic.enums.ItemStatus

data class ItemQuickInfo(
    val status: ItemStatus,
    val opponentId: String,
    val opponentName: String,
    val opponentIsVerified: Boolean,
    val opponentOrganizationName: String?,
    val opponentDonated: Int,
    val opponentReceived: Int
)