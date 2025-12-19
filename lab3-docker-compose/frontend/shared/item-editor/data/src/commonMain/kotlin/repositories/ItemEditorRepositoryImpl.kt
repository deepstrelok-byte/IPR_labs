package repositories

import dto.toDTO
import dto.toDomain
import entities.AICreateHelp
import entities.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.ItemEditorRemoteDataSource
import network.NetworkState

class ItemEditorRepositoryImpl(
    private val remoteDataSource: ItemEditorRemoteDataSource
) : ItemEditorRepository {
    override fun createItem(item: Item): Flow<NetworkState<Unit>> =
        remoteDataSource.createItem(item.toDTO())

    override fun updateItem(
        item: Item,
        itemId: String
    ): Flow<NetworkState<Unit>> = remoteDataSource.editItem(item.toDTO(), itemId)

    override fun askAICreateHelp(images: List<ByteArray>): Flow<NetworkState<AICreateHelp>> = flow {
        remoteDataSource.askAICreateHelp(images).collect { aiResponse ->
            emit(
                aiResponse.defaultWhen { response ->
                    NetworkState.Success(response.data.toDomain())
                }
            )
        }
    }.flowOn(Dispatchers.IO)
}