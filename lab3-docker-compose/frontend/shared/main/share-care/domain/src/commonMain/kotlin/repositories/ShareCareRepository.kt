package repositories

import entities.ShareCareItems
import entity.RequestResponse
import entity.SearchRequest
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface ShareCareRepository {
    fun fetchItems(): Flow<NetworkState<ShareCareItems>>

    fun search(searchRequest: SearchRequest): Flow<NetworkState<List<RequestResponse>>>
}