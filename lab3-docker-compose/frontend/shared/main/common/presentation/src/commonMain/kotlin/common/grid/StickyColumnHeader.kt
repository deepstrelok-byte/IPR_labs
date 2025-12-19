@file:OptIn(ExperimentalSharedTransitionApi::class)

package common.grid

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import view.consts.Paddings

@Suppress("FunctionName")
fun LazyGridScope.ColumnHeader(
    key: Any,
    text: String,
    paddings: PaddingValues = PaddingValues(vertical = Paddings.small),
    content: @Composable ColumnScope.() -> Unit
) {
    item(
        key = key,
        span = { GridItemSpan(maxLineSpan) }
    ) {

        Column(
            Modifier.animateItem().padding(horizontal = Paddings.semiSmall)
        ) {
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Medium,
                style = typography.headlineLargeEmphasized,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = typography.headlineLargeEmphasized.fontSize
                )
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddings),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}

@Suppress("FunctionName")
fun LazyGridScope.TransitionColumnHeader(
    contentType: ContentType,
) {
    item(
        key = contentType.key,
        span = { GridItemSpan(maxLineSpan) },
        contentType = contentType
    ) {
        val currentContentType = LocalCurrentContentType.current
        TransitionHeader(
            isVisible = currentContentType != contentType || currentContentType == ContentType.Catalog || currentContentType == ContentType.PeopleSearch,
            contentType = contentType,
            modifier = Modifier.animateItem().padding(horizontal = Paddings.semiSmall)
        )
    }
}

@Composable
fun TransitionHeader(
    contentType: ContentType,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Column { // lol, but with column animation is better...
        AnimatedContent(
            if (isVisible) contentType.parseName() else "",
            transitionSpec = { (fadeIn()).togetherWith(fadeOut()) }
        ) { text ->
            Text(
                text = text,
                modifier =
                    modifier,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Medium,
                style = typography.headlineLargeEmphasized,
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = typography.headlineLargeEmphasized.fontSize
                )
            )
        }
    }
}