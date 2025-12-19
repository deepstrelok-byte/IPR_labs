package findHelp.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import common.detailsInterfaces.DetailsConfig
import common.search.SearchData
import common.search.defaultOnSearch
import decompose.componentCoroutineScope
import entities.FindHelpBasic
import entity.ItemResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import network.NetworkState.AFK.saveState
import network.NetworkState.AFK.updateItemInList
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases
import usecases.FindHelpUseCases

class RealFindHelpComponent(
    componentContext: ComponentContext,
    override val openDetails: (cfg: DetailsConfig) -> Unit
) : FindHelpComponent, KoinComponent, ComponentContext by componentContext {
    private val findHelpUseCases: FindHelpUseCases = get()
    private val authUseCases: AuthUseCases = get()
    private val coroutineScope = componentCoroutineScope()
    override val myId: String = authUseCases.fetchUserId()!!

    override val basic: MutableStateFlow<NetworkState<FindHelpBasic>> =
        MutableStateFlow(NetworkState.AFK)


    init {
        fetchBasic()
    }

    override fun fetchBasic() {
        coroutineScope.launchIO {
            findHelpUseCases.fetchBasic().collect {
                val prevData = basic.value.data
                basic.value = it.saveState(
                    prevData,
                    onError = { response ->
                        if (response.data != null) {
                            AlertsManager.push(
                                AlertState.SnackBar("Не удалось обновить")
                            )
                        }
                        response
                    })
            }
        }.invokeOnCompletion {
            basic.value = basic.value.onCoroutineDeath()
        }
    }

    override fun takeItem(itemId: String, telegram: String) {
        fetchBasic()
        items.value = items.value.updateItemInList(
            predicate = { it.id == itemId }
        ) { it.copy(telegram = telegram, recipientId = myId) }
    }

    override fun denyItem(itemId: String) {
        fetchBasic()
        if (itemId in (items.value.data ?: listOf()).map { it.id }) {
            items.value = items.value.updateItemInList(
                predicate = { it.id == itemId }
            ) { it.copy(telegram = null, recipientId = null) }
        } else {
            onSearch(true)
        }
    }


    override val items: MutableStateFlow<NetworkState<List<ItemResponse>>> =
        MutableStateFlow(NetworkState.AFK)
    override val searchHasMoreItems: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    override val searchData: MutableStateFlow<SearchData> = MutableStateFlow(
        SearchData(category = null, deliveryTypes = listOf(), query = "")
    )

    override fun onQueryChange(query: String) {
        if (query != searchData.value.query) {
            searchData.value = searchData.value.copy(query = query)
            onSearch(resetItems = true)
        }
    }

    override fun onCategoryChange(category: ItemCategory?) {
        searchData.value = searchData.value.copy(category = category)
        onSearch(resetItems = true)
    }

    override fun onDeliveryTypesChange(deliveryTypes: List<DeliveryType>) {
        searchData.value = searchData.value.copy(deliveryTypes = deliveryTypes)
        onSearch(resetItems = true)
    }

    private var searchJob: Job? = null
    override fun onSearch(resetItems: Boolean) {
        searchJob = defaultOnSearch(
            items = items,
            searchHasMoreItems = searchHasMoreItems,
            searchData = searchData.value,
            coroutineScope = coroutineScope,
            prevJob = searchJob,
            toLoad = 20,
            resetItems = resetItems
        ) { findHelpUseCases.search(it) } ?: searchJob
    }
}