package widgets.expressiveList.cardButton

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import view.consts.Paddings
import widgets.expressiveList.ExpressiveListItem

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    item: ExpressiveListItem,
    textMaxLines: Int = Int.MAX_VALUE,
    minHeight: Dp = CardButtonDefaults.minCardButtonHeight,
    contentPadding: PaddingValues = PaddingValues(
        vertical = Paddings.semiMedium,
        horizontal = Paddings.small
    ),
    shape: Shape = CardButtonDefaults.shape,
    containerColor: Color = colorScheme.surfaceContainerHigh,
    contentColor: Color = colorScheme.contentColorFor(containerColor)
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(
            modifier = modifier
                .heightIn(min = minHeight)
                .clip(shape)
                .background(containerColor)
                .clickable(onClick = item.onClick),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item.icon?.let {
                    Icon(
                        item.icon, null,
                        modifier = Modifier
                            .padding(bottom = Paddings.semiSmall)
                            .sizeIn(maxHeight = CardButtonDefaults.maxIconSize)
                            .aspectRatio(1f)
                    )
                }
                Text(
                    item.text,
                    textAlign = TextAlign.Center,
                    style = typography.bodyLarge,
                    maxLines = textMaxLines,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = (typography.bodyLarge.fontSize.value + 4).sp
                )
            }
        }
    }
}