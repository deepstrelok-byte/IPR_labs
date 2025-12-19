package repositories

import entities.FindHelpBasic
import entity.ItemResponse
import entity.SearchRequest
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface FindHelpRepository {
    fun fetchBasic(): Flow<NetworkState<FindHelpBasic>>

    fun search(searchRequest: SearchRequest): Flow<NetworkState<List<ItemResponse>>>
}