package ktor

import dto.ItemQuickInfoDTO
import dto.TakeItemResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import network.NetworkState

class ItemDetailsRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {

    fun takeItem(itemId: String): Flow<NetworkState<TakeItemResponseDTO>> =
        hc.defaultPatch("$TAKE_ITEM_PATH/$itemId", tokenProvider)

    fun acceptItem(itemId: String): Flow<NetworkState<Unit>> =
        hc.defaultPatch("$ACCEPT_ITEM_PATH/$itemId", tokenProvider)

    fun denyItem(itemId: String): Flow<NetworkState<Unit>> =
        hc.defaultPatch("$DENY_ITEM_PATH/$itemId", tokenProvider)

    fun deleteItem(itemId: String): Flow<NetworkState<Unit>> =
        hc.defaultDelete(DELETE_ITEM_PATH, tokenProvider) {
            parameter("item_id", itemId)
        }

    fun fetchItemQuickInfo(itemId: String): Flow<NetworkState<ItemQuickInfoDTO>> =
        hc.defaultGet("$FETCH_ITEM_QUICK_INFO_PATH/$itemId", tokenProvider = tokenProvider)

    private companion object {
        const val TAKE_ITEM_PATH = "findhelp/take"

        const val ACCEPT_ITEM_PATH = "items/accept"
        const val DENY_ITEM_PATH = "items/deny"

        const val DELETE_ITEM_PATH = "items/"

        const val FETCH_ITEM_QUICK_INFO_PATH = "items/quick-info"
    }

}