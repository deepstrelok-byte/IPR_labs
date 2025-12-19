package common.grid

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import foundation.scrollables.overscrollEffect.forPullToRefresh.rememberCupertinoPullToRefreshOverscrollFactory
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import view.consts.Sizes


data class SpacePaddings(
    val top: Dp,
    val bottom: Dp
)

val LocalSpacePaddings: ProvidableCompositionLocal<SpacePaddings> =
    staticCompositionLocalOf {
        error("No SpacePaddings provided")
    }

@Composable
fun getPullRefreshTopPadding(
    state: PullToRefreshState?
): Dp {
    return if (state != null) {
        state.distanceFraction * PullToRefreshDefaults.IndicatorMaxDistance
    } else {
        0.dp
    }
}

@Composable
fun Modifier.pullRefreshContentTransform(
    state: PullToRefreshState?,
) =
    this
        .padding(
            top = getPullRefreshTopPadding(state)
        )


@Composable
fun MainLazyGrid(
    lazyGridState: LazyGridState = rememberLazyGridState(),
    onRefresh: () -> Unit = {},
    isRefreshing: Boolean = false,
    content: LazyGridScope.() -> Unit
) {

    val spacePaddings = LocalSpacePaddings.current

    var isPullToRefreshInitiator by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        // Завершили загрузку: pullToRefresh больше не инициатор рефреша
        if (!isRefreshing) isPullToRefreshInitiator = false
    }

    val pullToRefreshState = rememberPullToRefreshState()
    CompositionLocalProvider(
        LocalOverscrollFactory provides rememberCupertinoPullToRefreshOverscrollFactory()
    ) {
        PullToRefreshBox(
            isRefreshing = isRefreshing && isPullToRefreshInitiator,
            onRefresh = {
                isPullToRefreshInitiator = true
                onRefresh()
            },
            state = pullToRefreshState,
            indicator = {
                PullToRefreshDefaults.LoadingIndicator(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter)
                        .padding(top = spacePaddings.top - Paddings.medium),
                    maxDistance = PullToRefreshDefaults.IndicatorMaxDistance
                )
            }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(Sizes.columnWidth),
                state = lazyGridState,
                contentPadding = PaddingValues(horizontal = Paddings.listHorizontalPadding),
                verticalArrangement = Arrangement.spacedBy(Paddings.small),
                horizontalArrangement = Arrangement.spacedBy(Paddings.small),
                modifier = Modifier.fillMaxSize().fastBackground(colorScheme.background)
                    .pullRefreshContentTransform(pullToRefreshState)
            ) {
                item(key = "topPadding", span = { GridItemSpan(maxLineSpan) }) {
                    SpacerV(spacePaddings.top)
                }
                content()

                item(key = "bottomPadding", span = { GridItemSpan(maxLineSpan) }) {
                    SpacerV(spacePaddings.bottom)
                }
            }
        }
    }

}
