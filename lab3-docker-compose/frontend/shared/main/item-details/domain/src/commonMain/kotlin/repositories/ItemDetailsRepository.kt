package repositories

import entities.TakeItemResponse
import entity.ItemQuickInfo
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface ItemDetailsRepository {
    fun takeItem(itemId: String): Flow<NetworkState<TakeItemResponse>>
    fun acceptItem(itemId: String): Flow<NetworkState<Unit>>
    fun denyItem(itemId: String): Flow<NetworkState<Unit>>

    fun deleteItem(itemId: String): Flow<NetworkState<Unit>>

    fun fetchItemQuickInfo(itemId: String): Flow<NetworkState<ItemQuickInfo>>
}