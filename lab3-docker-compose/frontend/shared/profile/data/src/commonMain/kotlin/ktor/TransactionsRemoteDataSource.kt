package ktor

import dto.TransactionDTO
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import network.NetworkState

class TransactionsRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {

    fun fetchTransactions(userId: String): Flow<NetworkState<List<TransactionDTO>>> =
        hc.defaultGet(path = "$FETCH_TRANSACTIONS_PATH/$userId", tokenProvider = tokenProvider)

    companion object {
        const val FETCH_TRANSACTIONS_PATH = "/items/transactions"
    }

}