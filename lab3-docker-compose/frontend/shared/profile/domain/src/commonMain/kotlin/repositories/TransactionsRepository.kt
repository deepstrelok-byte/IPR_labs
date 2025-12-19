package repositories

import entities.Transaction
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface TransactionsRepository {
    fun fetchTransactions(userId: String): Flow<NetworkState<List<Transaction>>>
}