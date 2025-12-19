package itemManager.components

import androidx.compose.foundation.text.input.TextFieldState
import entities.AICreateHelp
import kotlinx.coroutines.flow.StateFlow
import logic.ItemManagerPreData
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import photoTaker.components.PhotoTakerComponent

interface ItemManagerComponent {



    val aiAnswer: StateFlow<NetworkState<AICreateHelp>>
    fun askAI()



    val isEditing: Boolean

    val itemManagerPreData: ItemManagerPreData

    val createOrEditItemResult: StateFlow<NetworkState<Unit>>

    val title: TextFieldState
    val description: TextFieldState

    val deliveryTypes: StateFlow<List<DeliveryType>>
    val itemCategory: StateFlow<ItemCategory?>

    fun updateDeliveryType(deliveryType: DeliveryType)
    fun updateItemCategory(itemCategory: ItemCategory)

    fun createOrEditItem()


    val photoTakerComponent: PhotoTakerComponent
    val closeFlow: () -> Unit
    val openPhotoTakerComponent: () -> Unit
}