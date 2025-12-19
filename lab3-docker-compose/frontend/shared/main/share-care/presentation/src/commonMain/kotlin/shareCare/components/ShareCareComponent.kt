package shareCare.components

import common.detailsInterfaces.DetailsConfig
import common.search.SearchData
import entities.ShareCareItems
import entity.ItemResponse
import entity.RequestResponse
import kotlinx.coroutines.flow.StateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState

interface ShareCareComponent {


    fun denyItem(itemId: String)


    val items: StateFlow<NetworkState<ShareCareItems>>

    fun fetchItems()

    val openDetails: (cfg: DetailsConfig) -> Unit

    val requests: StateFlow<NetworkState<List<RequestResponse>>>
    val searchHasMoreRequests: StateFlow<Boolean>
    val searchData: StateFlow<SearchData>
    fun onQueryChange(query: String)
    fun onCategoryChange(category: ItemCategory?)
    fun onDeliveryTypesChange(deliveryTypes: List<DeliveryType>)
    fun onSearch(resetItems: Boolean)
}