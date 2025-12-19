package mainFlow.ui.topBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.grid.ContentType
import common.grid.TransitionHeader
import dev.chrisbanes.haze.HazeState
import utils.SpacerH
import view.consts.Paddings
import widgets.glass.GlassCard

@Composable
internal fun TopTitle(
    modifier: Modifier = Modifier,
    isTitle: Boolean,
    hazeState: HazeState,
    contentType: ContentType?
) {
    Row(modifier) {
        TitleAnimateVisibility(isTitle, isSlide = false) {
            SpacerH(Paddings.listHorizontalPadding)
        }

        GlassCard(
            contentPadding = if (isTitle) PaddingValues(
                vertical = Paddings.semiSmall,
                horizontal = Paddings.semiMedium
            ) else PaddingValues.Zero,
            hazeState = hazeState,
            isReversedProgressive = true,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(Modifier.animateContentSize()) {
                TitleAnimateVisibility(isTitle, isSlide = true) {
                    Box(
                        Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        TransitionHeader(
                            contentType = contentType ?: ContentType.Catalog,
                            isVisible = true
                        )
                    }
                }
            }

        }


    }
}

@Composable
private fun RowScope.TitleAnimateVisibility(
    isTitle: Boolean,
    isSlide: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        isTitle,
        enter = fadeIn(tween(600)) + if (isSlide) slideInHorizontally() else expandHorizontally(),
        exit = fadeOut(tween(500)) + if (isSlide) slideOutHorizontally() else shrinkHorizontally()
    ) {
        content()
    }
}