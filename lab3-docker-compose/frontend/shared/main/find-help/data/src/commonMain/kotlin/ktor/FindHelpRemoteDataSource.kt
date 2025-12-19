package ktor

import dto.ItemResponseDTO
import dto.SearchRequestDTO
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ktor.models.response.FindHelpBasicResponse
import network.NetworkState

class FindHelpRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {
    fun fetchBasic(): Flow<NetworkState<FindHelpBasicResponse>> =
        hc.defaultGet(path = FETCH_BASIC_PATH, tokenProvider)

    fun fetchSearch(request: SearchRequestDTO): Flow<NetworkState<List<ItemResponseDTO>>> =
        hc.defaultPost(path = SEARCH_PATH, tokenProvider, body = request)


    private companion object {
        const val PRE_PATH = "/findhelp"
        const val FETCH_BASIC_PATH = "$PRE_PATH/basic"
        const val SEARCH_PATH = "$PRE_PATH/search"
    }

}