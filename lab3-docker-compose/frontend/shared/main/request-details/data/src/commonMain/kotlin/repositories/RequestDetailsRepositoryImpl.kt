package repositories

import dto.toDTO
import entities.Request
import entity.ItemQuickInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.RequestDetailsRemoteDataSource
import network.NetworkState

class RequestDetailsRepositoryImpl(
    val remoteDataSource: RequestDetailsRemoteDataSource
) : RequestDetailsRepository {
    override fun createRequest(request: Request): Flow<NetworkState<Unit>> =
        remoteDataSource.createRequest(request.toDTO())

    override fun editRequest(
        request: Request,
        requestId: String
    ): Flow<NetworkState<Unit>> =
        remoteDataSource.editRequest(request.toDTO(requestId))

    override fun deleteRequest(requestId: String): Flow<NetworkState<Unit>> =
        remoteDataSource.deleteRequest(requestId)

    override fun fetchRequestQuickInfo(requestId: String): Flow<NetworkState<ItemQuickInfo>> = flow {
        remoteDataSource.fetchRequestQuickInfo(requestId).collect { infoResponse ->
            emit(
                infoResponse.defaultWhen { response ->
                    NetworkState.Success(response.data.toDomain())
                }
            )
        }
    }.flowOn(Dispatchers.IO)
}