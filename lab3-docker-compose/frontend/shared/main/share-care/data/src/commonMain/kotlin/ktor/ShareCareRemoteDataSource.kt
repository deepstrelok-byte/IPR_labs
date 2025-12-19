package ktor

import dto.RequestResponseDTO
import dto.SearchRequestDTO
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ktor.models.response.ShareCareItemsResponse
import network.NetworkState

class ShareCareRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {
    fun fetchItems(): Flow<NetworkState<ShareCareItemsResponse>> =
        hc.defaultGet(path = FETCH_ITEMS_PATH, tokenProvider)

    fun fetchSearch(request: SearchRequestDTO): Flow<NetworkState<List<RequestResponseDTO>>> =
        hc.defaultPost(path = SEARCH_PATH, tokenProvider, body = request)

    private companion object {
        const val PRE_PATH = "/sharecare"
        const val FETCH_ITEMS_PATH = "$PRE_PATH/items"
        const val SEARCH_PATH = "$PRE_PATH/search"
    }

}