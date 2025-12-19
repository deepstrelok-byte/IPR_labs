package utils

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged

// https://dev.to/luismierez/infinite-lazycolumn-in-jetpack-compose-44a4?ysclid=migjh6ntil778358051


@Composable
fun GridEndHandler(
    gridState: LazyGridState,
    items: Any?,
    preFetchItems: Int,
    hasMoreItems: Boolean,
    isLoading: Boolean,
    onLoadMore: () -> Unit
) {
    // TODO: вроде работает, но надо почекать производительность
    @Suppress("FrequentlyChangingValue")
    LaunchedEffect(items, gridState, hasMoreItems, isLoading) {
        snapshotFlow {
            if (isLoading || !hasMoreItems) return@snapshotFlow false

            val layoutInfo = gridState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount

            if (totalItemsNumber == 0) return@snapshotFlow true

            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            (lastVisibleItemIndex > (totalItemsNumber - preFetchItems)) || !gridState.canScrollForward
        }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad) {
                    onLoadMore()
                }
            }
    }
}