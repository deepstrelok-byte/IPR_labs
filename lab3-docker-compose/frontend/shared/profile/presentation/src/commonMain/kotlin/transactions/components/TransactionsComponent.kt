package transactions.components

import entities.Transaction
import kotlinx.coroutines.flow.StateFlow
import logic.QuickProfileData
import network.NetworkState

interface TransactionsComponent {
    val profileData: QuickProfileData
    val userId: String

    val pop: () -> Unit


    val transactionsInfo: StateFlow<TransactionsInfo?>
    val transactions: StateFlow<NetworkState<List<Transaction>>>
    fun fetchTransactions()


    data class TransactionsInfo(
        val received: Int,
        val donated: Int
    )

}