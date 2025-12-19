package repositories

import entities.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.TransactionsRemoteDataSource
import network.NetworkState

class TransactionsRepositoryImpl(
    private val remoteDataSource: TransactionsRemoteDataSource
): TransactionsRepository {
    override fun fetchTransactions(userId: String): Flow<NetworkState<List<Transaction>>> = flow {
        remoteDataSource.fetchTransactions(userId).collect { transactionsResponse ->
            emit(
                transactionsResponse.defaultWhen { response ->
                    NetworkState.Success(response.data.map { it.toDomain() })
                }
            )
        }
    }.flowOn(Dispatchers.IO)
}