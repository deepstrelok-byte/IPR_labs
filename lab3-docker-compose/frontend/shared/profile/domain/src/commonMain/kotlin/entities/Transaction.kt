package entities

data class Transaction(
    val itemId: String,
    val title: String,
    val description: String,
    val imageLink: String,
    val isRecipient: Boolean,
    val dateOfReceipt: String // Дата получения
)
