package itemManager.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import entities.AICreateHelp
import entities.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import logic.ItemManagerPreData
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import photoTaker.components.PhotoTakerComponent
import usecases.ItemEditorUseCases
import utils.encodeToByteArray

class RealItemManagerComponent(
    componentContext: ComponentContext,
    override val photoTakerComponent: PhotoTakerComponent,
    override val closeFlow: () -> Unit,
    override val openPhotoTakerComponent: () -> Unit,
    override val itemManagerPreData: ItemManagerPreData,
    private val fetchShareCareItems: () -> Unit
) : ItemManagerComponent, KoinComponent, ComponentContext by componentContext {
    private val itemEditorUseCases: ItemEditorUseCases = get()

    private val coroutineScope = componentCoroutineScope()
    override val aiAnswer: MutableStateFlow<NetworkState<AICreateHelp>> =
        MutableStateFlow(NetworkState.AFK)

    override fun askAI() {
        if (!aiAnswer.value.isLoading()) {
            coroutineScope.launchIO {
                itemEditorUseCases.askAICreateHelp(images = getImages()).collect {
                    aiAnswer.value = it
                    it.onError { error ->
                        AlertsManager.push(AlertState.SnackBar(error.prettyPrint))
                    }
                }
            }.invokeOnCompletion {
                aiAnswer.value = aiAnswer.value.onCoroutineDeath()
            }
        }
    }

    override val isEditing: Boolean
        get() = itemManagerPreData.description.isNotEmpty()
    override val createOrEditItemResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)
    override val title = TextFieldState(initialText = itemManagerPreData.title)
    override val description = TextFieldState(initialText = itemManagerPreData.description)


    override val deliveryTypes = MutableStateFlow(itemManagerPreData.deliveryTypes)

    override val itemCategory = MutableStateFlow(itemManagerPreData.category)


    override fun updateItemCategory(itemCategory: ItemCategory) {
        if (!createOrEditItemResult.value.isLoading() && (itemManagerPreData.category == null || isEditing)) {
            this.itemCategory.value = itemCategory
        }
    }

    override fun updateDeliveryType(deliveryType: DeliveryType) {
        if (!createOrEditItemResult.value.isLoading()) {
            if (deliveryType in this.deliveryTypes.value) {
                removeDeliveryType(deliveryType)
            } else {
                addDeliveryType(deliveryType)
            }
        }
    }


    override fun createOrEditItem() {
        if (!createOrEditItemResult.value.isLoading()) {
            coroutineScope.launchIO {
                val preparedItem: Item = Item(
                    images = getImages(),
                    title = title.text.toString(),
                    description = description.text.toString(),
                    category = itemCategory.value!!,
                    deliveryTypes = deliveryTypes.value,
                    location = "Москва, м. Сокол", // TODO
                    requestId = itemManagerPreData.requestId
                )
                (if (isEditing) itemEditorUseCases.updateItem(
                    item = preparedItem,
                    itemId = itemManagerPreData.itemId!!
                ) else itemEditorUseCases.createItem(item = preparedItem)).collect {
                    createOrEditItemResult.value = it
                }
                withContext(Dispatchers.Main) {
                    createOrEditItemResult.value.handle(
                        onError = { AlertsManager.push(AlertState.SnackBar(it.prettyPrint)) }
                    ) {
                        AlertsManager.push(
                            AlertState.SuccessDialog(if (isEditing) "Предмет обновлён" else "Предмет создан")
                        )
                        fetchShareCareItems()
                        closeFlow()
                    }
                }

            }.invokeOnCompletion {
                createOrEditItemResult.value = createOrEditItemResult.value.onCoroutineDeath()
            }
        }
    }

    private fun addDeliveryType(deliveryType: DeliveryType) {
        this.deliveryTypes.update { current -> current + deliveryType }
    }

    private fun removeDeliveryType(deliveryType: DeliveryType) {
        this.deliveryTypes.update { current -> current - deliveryType }
    }

    private suspend fun getImages() =
        photoTakerComponent.pickedPhotos.value.map { it.encodeToByteArray(50) }
}