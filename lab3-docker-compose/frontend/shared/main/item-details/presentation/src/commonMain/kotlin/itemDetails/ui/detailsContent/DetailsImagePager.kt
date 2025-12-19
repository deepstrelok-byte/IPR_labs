package itemDetails.ui.detailsContent

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import common.itemCard.ItemImage
import utils.fastBackground
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsImagePager(
    images: List<String>,
    isStableDetailed: Boolean,
    modifier: Modifier,
    state: PagerState,
    itemKey: String?
) {
    Crossfade(
        isStableDetailed,
        modifier = modifier,
        animationSpec = tween(if (state.currentPage == 0) 0 else 300)
    ) { isPager ->
        if (isPager) {
            HorizontalPager(
                state = state,
                modifier = Modifier//.align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fastBackground(if (isStableDetailed) colorScheme.background else Color.Transparent)
            ) { index ->
                ItemImage(
                    imagePath = images[index],
                    modifier = Modifier
                        .padding(horizontal = Paddings.listHorizontalPadding)
                        .fillMaxSize(),
                    key = if (index == 0) itemKey else itemKey+index,
                    detailedItemKey = null,
                    animatedContentScope = null
                )
            }
        }
    }
}