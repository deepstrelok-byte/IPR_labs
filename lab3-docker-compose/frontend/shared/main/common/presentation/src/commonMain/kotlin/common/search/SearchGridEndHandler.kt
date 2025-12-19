package common.search

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import network.NetworkState
import utils.GridEndHandler

@Composable
fun <T> SearchGridEndHandler(
    lazyGridState: LazyGridState,
    preFetchItems: Int = 20,
    searchHasMoreItems: Boolean,
    searchItems: NetworkState<List<T>>,
    onSearch: () -> Unit
) {
    GridEndHandler(
        gridState = lazyGridState,
        preFetchItems = preFetchItems,
        hasMoreItems = searchHasMoreItems,
        isLoading = searchItems.isLoading(),
        items = searchItems.data
    ) {
        if (!searchItems.isErrored() || searchItems.data != null) {
            onSearch()
        }
    }
}