package shareCare.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import common.detailsInterfaces.DetailsConfig
import common.search.SearchData
import common.search.defaultOnSearch
import decompose.componentCoroutineScope
import entities.ShareCareItems
import entity.RequestResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import network.NetworkState.AFK.saveState
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.ShareCareUseCases

class RealShareCareComponent(
    componentContext: ComponentContext,
    override val openDetails: (cfg: DetailsConfig) -> Unit
) : ShareCareComponent, KoinComponent, ComponentContext by componentContext {

    private val shareCareUseCases: ShareCareUseCases = get()
    private val coroutineScope = componentCoroutineScope()


    override val items: MutableStateFlow<NetworkState<ShareCareItems>> =
        MutableStateFlow(NetworkState.AFK)


    init {
        fetchItems()
    }


    override fun denyItem(itemId: String) {
        fetchItems()
    }


    override fun fetchItems() {
        coroutineScope.launchIO {
            shareCareUseCases.fetchItems().collect {
                val prevData = items.value.data

                items.value = it.saveState(
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
            items.value = items.value.onCoroutineDeath()
        }
    }

    override val requests: MutableStateFlow<NetworkState<List<RequestResponse>>> =
        MutableStateFlow(NetworkState.AFK)
    override val searchHasMoreRequests: MutableStateFlow<Boolean> =
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
            items = requests,
            searchHasMoreItems = searchHasMoreRequests,
            searchData = searchData.value,
            coroutineScope = coroutineScope,
            prevJob = searchJob,
            toLoad = 20,
            resetItems = resetItems
        ) { shareCareUseCases.search(it) } ?: searchJob
    }
}