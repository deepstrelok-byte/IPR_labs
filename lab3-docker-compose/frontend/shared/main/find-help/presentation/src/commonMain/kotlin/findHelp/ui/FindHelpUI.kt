package findHelp.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import common.grid.ContentType
import common.grid.MainLazyGrid
import common.grid.defaults.DefaultItemCard
import common.itemDetailsTransition.LocalItemDetailsAnimator
import common.search.SearchGridEndHandler
import common.search.SearchSection
import findHelp.components.FindHelpComponent
import findHelp.ui.sections.BasicSection

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FindHelpUI(
    lazyGridState: LazyGridState,
    component: FindHelpComponent
) {
    val itemDetailsAnimator = LocalItemDetailsAnimator.current


    val basic by component.basic.collectAsState()

    val searchItems by component.items.collectAsState()
    val searchData by component.searchData.collectAsState()
    val searchHasMoreItems by component.searchHasMoreItems.collectAsState()

    MainLazyGrid(
        lazyGridState = lazyGridState,
        isRefreshing = searchItems.isLoading() || basic.isLoading(),
        onRefresh = {
            component.onSearch(resetItems = true)
            component.fetchBasic()
        }
    ) {
        if (searchData.query.isEmpty()) {
            BasicSection(
                basic = basic,
                sharedTransitionScope = this@FindHelpUI,
                itemDetailsAnimator = itemDetailsAnimator,
                onClick = component.openDetails,
                refreshClick = component::fetchBasic
            )
        }

        SearchSection(
            searchResponse = searchItems,
            searchData = searchData,
            onDeliveryTypesChange = component::onDeliveryTypesChange,
            onCategoryChange = component::onCategoryChange,
            refreshClick = { component.onSearch(resetItems = true) },
            hasMoreItems = searchHasMoreItems,
            key = { it.id },
            contentType = ContentType.Catalog
        ) { item, key ->
            DefaultItemCard(
                item = item,
                key = key,
                itemDetailsAnimator = itemDetailsAnimator,
                myId = component.myId,
                sharedTransitionScope = this@FindHelpUI,
                onCardClicked = component.openDetails
            )
        }
    }

    SearchGridEndHandler(
        lazyGridState = lazyGridState,
        searchHasMoreItems = searchHasMoreItems,
        searchItems = searchItems
    ) { component.onSearch(resetItems = false) }
}

//@Preview(showBackground = true)
//@Composable
//fun FindHelpUIPreview(
//    topSafePadding: Dp,
//    bottomShadowHeight: Dp,
//    emptyBottomPadding: Dp,
//    hazeState: HazeState,
//) {
//    FindHelpUI(
//        topSafePadding = topSafePadding,
//        bottomShadowHeight = bottomShadowHeight,
//        emptyBottomPadding = emptyBottomPadding,
//        hazeState = hazeState,
//        component = FakeFindHelpComponent
//    )
//}