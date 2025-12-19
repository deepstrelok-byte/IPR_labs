package itemDetails.components

import common.detailsInterfaces.DetailsComponent
import entities.TakeItemResponse
import entity.ItemQuickInfo
import kotlinx.coroutines.flow.StateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState

interface ItemDetailsComponent: DetailsComponent {

    fun openTelegram()

    fun onProfileClick()

    val onEditClick: () -> Unit

    val title: String
    val description: String
    val location: String
    val category: ItemCategory
    val deliveryTypes: List<DeliveryType>

    val images: List<String>


    val recipientId: StateFlow<String?>


    val isOwner: Boolean

    val telegram: StateFlow<String?>


    val takeItemResult: StateFlow<NetworkState<TakeItemResponse>>
    val operationItemResult: StateFlow<NetworkState<Unit>>

    fun takeItem()
    fun acceptItem(closeSheet: (() -> Unit) -> Unit)
    fun denyItem()

    fun deleteItem(closeSheet: (() -> Unit) -> Unit)



    val itemQuickInfo: StateFlow<NetworkState<ItemQuickInfo>>
    fun fetchItemQuickInfo()

}