package common.search

import alertsManager.AlertState
import alertsManager.AlertsManager
import architecture.launchIO
import entity.SearchRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import network.NetworkState.AFK.saveState

fun <T> defaultOnSearch(
    items: MutableStateFlow<NetworkState<List<T>>>,
    searchHasMoreItems: MutableStateFlow<Boolean>,
    searchData: SearchData,
    coroutineScope: CoroutineScope,
    prevJob: Job?,
    toLoad: Int,
    resetItems: Boolean,
    search: (SearchRequest) -> Flow<NetworkState<List<T>>>
): Job? {
    var job: Job? = null
    if (!items.value.isLoading() && prevJob?.isActive != true) {
        var isHaveToDelayAfterError = false
        val errorDelay = 3000L
        if (resetItems) searchHasMoreItems.value = true

        val data = searchData
        val searchRequest = SearchRequest(
            query = data.query,
            category = data.category,
            deliveryTypes = data.deliveryTypes,
            offset = if (items.value is NetworkState.Success && !resetItems) (items.value as NetworkState.Success<List<T>>).data.size else 0,
            toLoad = toLoad
        )
        job = coroutineScope.launchIO {
            search(searchRequest).collect { response ->
                val prevItems = items.value.data
                items.value = response.saveState(
                    prevData = prevItems,
                    onError = { response ->
                        if (response.data != null) {
                            AlertsManager.push(
                                AlertState.SnackBar("Не удалось загрузить новые объекты"),
                                duration = errorDelay / 2
                            )
                            isHaveToDelayAfterError = true
                        }
                        response
                    },
                    onSuccess = { response ->
                        val newData = response.data
                        response.copy(
                            data = if (resetItems) {
                                newData
                            } else {
                                (prevItems ?: listOf()) + newData
                            }
                        )
                    })
                response.handle { success ->
                    if (success.data.isEmpty()) searchHasMoreItems.value = false
                }
            }
            if (isHaveToDelayAfterError) {
                delay(errorDelay) // продолжаем работу корутины, чтобы не выполнялись новые запросы
            }
        }.also {
            it.invokeOnCompletion {
                items.value = items.value.onCoroutineDeath()
            }
        }
    }
    return job
}