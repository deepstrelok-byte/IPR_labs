package ktor

import dto.ItemQuickInfoDTO
import dto.RequestDTO
import dto.RequestWithIdDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import network.NetworkState

class RequestDetailsRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {

    fun createRequest(request: RequestDTO): Flow<NetworkState<Unit>> =
        hc.defaultPost(REQUESTS_PATH, tokenProvider, body = request)

    fun editRequest(request: RequestWithIdDTO): Flow<NetworkState<Unit>> =
        hc.defaultPatch(REQUESTS_PATH, tokenProvider, body = request)

    fun deleteRequest(requestId: String): Flow<NetworkState<Unit>> =
        hc.defaultDelete(REQUESTS_PATH, tokenProvider) {
            parameter("request_id", requestId)
        }

    fun fetchRequestQuickInfo(requestId: String): Flow<NetworkState<ItemQuickInfoDTO>> =
        hc.defaultGet("$FETCH_REQUEST_QUICK_INFO_PATH/$requestId", tokenProvider = tokenProvider)



    private companion object {
        const val REQUESTS_PATH = "/requests/"
        const val FETCH_REQUEST_QUICK_INFO_PATH = "/requests/quick-info"
    }
}