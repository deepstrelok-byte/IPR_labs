package dto

import entities.Transaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDTO(
    @SerialName("item_id") val itemId: String,
    val title: String,
    val description: String,
    @SerialName("image_link") val imageLink: String,
    @SerialName("is_recipient") val isRecipient: Boolean,
    @SerialName("date_of_receipt") val dateOfReceipt: String // Дата получения
) {
    fun toDomain() = Transaction(
        itemId = itemId,
        title = title,
        description = description,
        imageLink = imageLink,
        isRecipient = isRecipient,
        dateOfReceipt = dateOfReceipt
    )
}
