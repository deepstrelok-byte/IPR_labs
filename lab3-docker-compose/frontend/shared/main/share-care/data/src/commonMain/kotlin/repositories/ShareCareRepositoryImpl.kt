package repositories

import dto.toDTO
import dto.toDomain
import entities.ShareCareItems
import entity.RequestResponse
import entity.SearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.ShareCareRemoteDataSource
import network.NetworkState

class ShareCareRepositoryImpl(
    private val remoteDataSource: ShareCareRemoteDataSource
) : ShareCareRepository {
    override fun fetchItems(): Flow<NetworkState<ShareCareItems>> = flow {
        remoteDataSource.fetchItems().collect { itemsResponse ->
            emit(
                itemsResponse.defaultWhen { response ->
                    NetworkState.Success(response.data.toDomain())
                }
            )
        }
    }.flowOn(Dispatchers.IO)

    override fun search(searchRequest: SearchRequest): Flow<NetworkState<List<RequestResponse>>> =
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