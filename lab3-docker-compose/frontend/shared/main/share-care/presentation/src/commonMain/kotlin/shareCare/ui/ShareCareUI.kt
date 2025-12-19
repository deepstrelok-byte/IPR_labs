package shareCare.ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import common.grid.ContentType
import common.grid.MainLazyGrid
import common.grid.defaults.DefaultRequestCard
import common.itemDetailsTransition.LocalItemDetailsAnimator
import common.search.SearchGridEndHandler
import common.search.SearchSection
import shareCare.components.ShareCareComponent
import shareCare.ui.sections.ItemsSection

@Composable
fun SharedTransitionScope.ShareCareUI(
    lazyGridState: LazyGridState,
    component: ShareCareComponent,
) {

    val items by component.items.collectAsState()

    val itemDetailsAnimator = LocalItemDetailsAnimator.current

    val searchRequests by component.requests.collectAsState()
    val searchData by component.searchData.collectAsState()
    val searchHasMoreRequests by component.searchHasMoreRequests.collectAsState()



    MainLazyGrid(
        lazyGridState = lazyGridState,
        isRefreshing = searchRequests.isLoading() || items.isLoading(),
        onRefresh = {
            component.onSearch(resetItems = true)
            component.fetchItems()
        }
    ) {
        if (searchData.query.isEmpty()) {
            ItemsSection(
                items,
                sharedTransitionScope = this@ShareCareUI,
                itemDetailsAnimator = itemDetailsAnimator,
                onClick = component.openDetails,
                refreshClick = component::fetchItems
            )
        }

        SearchSection(
            searchResponse = searchRequests,
            searchData = searchData,
            onDeliveryTypesChange = component::onDeliveryTypesChange,
            onCategoryChange = component::onCategoryChange,
            refreshClick = { component.onSearch(resetItems = true) },
            hasMoreItems = searchHasMoreRequests,
            key = { it.id },
            contentType = ContentType.PeopleSearch
        ) { request, key ->
            DefaultRequestCard(
                request = request,
                sharedTransitionScope = this@ShareCareUI,
                key = key,
                onCardClicked = component.openDetails
            )
        }
    }

    SearchGridEndHandler(
        lazyGridState = lazyGridState,
        searchHasMoreItems = searchHasMoreRequests,
        searchItems = searchRequests
    ) { component.onSearch(resetItems = false) }
}
