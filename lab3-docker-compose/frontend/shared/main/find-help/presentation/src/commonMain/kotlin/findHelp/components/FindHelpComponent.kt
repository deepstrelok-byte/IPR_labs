package findHelp.components

import common.detailsInterfaces.DetailsConfig
import common.search.SearchData
import entities.FindHelpBasic
import entity.ItemResponse
import kotlinx.coroutines.flow.StateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState

interface FindHelpComponent {
    val myId: String

    val basic: StateFlow<NetworkState<FindHelpBasic>>

    fun fetchBasic()


    val openDetails: (cfg: DetailsConfig) -> Unit


    fun takeItem(itemId: String, telegram: String)
    fun denyItem(itemId: String)


    val items: StateFlow<NetworkState<List<ItemResponse>>>
    val searchHasMoreItems: StateFlow<Boolean>
    val searchData: StateFlow<SearchData>
    fun onQueryChange(query: String)
    fun onCategoryChange(category: ItemCategory?)
    fun onDeliveryTypesChange(deliveryTypes: List<DeliveryType>)
    fun onSearch(resetItems: Boolean)
}