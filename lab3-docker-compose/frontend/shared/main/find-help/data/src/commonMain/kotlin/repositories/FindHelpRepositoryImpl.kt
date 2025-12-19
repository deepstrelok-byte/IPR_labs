package repositories

import dto.toDTO
import dto.toDomain
import entities.FindHelpBasic
import entity.ItemResponse
import entity.SearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.FindHelpRemoteDataSource
import network.NetworkState

class FindHelpRepositoryImpl(
    private val remoteDataSource: FindHelpRemoteDataSource
) : FindHelpRepository {
    override fun fetchBasic(): Flow<NetworkState<FindHelpBasic>> = flow {
        remoteDataSource.fetchBasic().collect { itemsResponse ->
            emit(
                itemsResponse.defaultWhen { response ->
                    NetworkState.Success(response.data.toDomain())
                }
            )
        }
    }.flowOn(Dispatchers.IO)

    override fun search(searchRequest: SearchRequest): Flow<NetworkState<List<ItemResponse>>> =
        flow {
            remoteDataSource.fetchSearch(searchRequest.toDTO()).collect { itemsResponse ->
                emit(
                    itemsResponse.defaultWhen { response ->
                        NetworkState.Success(response.data.map { it.toDomain() })
                    }
                )
            }
        }.flowOn(Dispatchers.IO)


}