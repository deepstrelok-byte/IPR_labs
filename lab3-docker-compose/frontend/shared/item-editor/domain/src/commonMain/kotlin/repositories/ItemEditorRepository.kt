package repositories

import entities.AICreateHelp
import entities.Item
import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface ItemEditorRepository {
    fun createItem(item: Item): Flow<NetworkState<Unit>>
    fun updateItem(item: Item, itemId: String): Flow<NetworkState<Unit>>

    fun askAICreateHelp(images: List<ByteArray>): Flow<NetworkState<AICreateHelp>>
}