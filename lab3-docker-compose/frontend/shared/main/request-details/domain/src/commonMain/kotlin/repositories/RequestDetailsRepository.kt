package repositories

import entities.Request
import entity.ItemQuickInfo
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface RequestDetailsRepository {
    fun createRequest(request: Request): Flow<NetworkState<Unit>>
    fun editRequest(request: Request, requestId: String): Flow<NetworkState<Unit>>
    fun deleteRequest(requestId: String): Flow<NetworkState<Unit>>

    fun fetchRequestQuickInfo(requestId: String): Flow<NetworkState<ItemQuickInfo>>
}