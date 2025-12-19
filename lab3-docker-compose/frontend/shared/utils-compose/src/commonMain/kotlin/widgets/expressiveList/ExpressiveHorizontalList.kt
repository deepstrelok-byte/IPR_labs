package widgets.expressiveList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import widgets.expressiveList.ExpressiveHorizontalListDefaults.endShape
import widgets.expressiveList.ExpressiveHorizontalListDefaults.fullShape
import widgets.expressiveList.ExpressiveHorizontalListDefaults.middleShape
import widgets.expressiveList.ExpressiveHorizontalListDefaults.startShape

object ExpressiveHorizontalListDefaults {
    val connection = 8.dp
    val corner = 20.dp
    val startShape = RoundedCornerShape(
        topStart = corner,
        bottomStart = corner,
        topEnd = connection,
        bottomEnd = connection
    )
    val middleShape = RoundedCornerShape(connection)
    val endShape = RoundedCornerShape(
        topStart = connection,
        bottomStart = connection,
        topEnd = corner,
        bottomEnd = corner
    )

    val fullShape = RoundedCornerShape(corner)
}

@Composable
fun ExpressiveHorizontalList(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    items: List<ExpressiveListItem>
) {
    Row(modifier = modifier) {
        if (items.size > 1) {
            items.forEachIndexed { index, item ->
                val shape = when (index) {
                    0 -> startShape
                    items.lastIndex -> endShape
                    else -> middleShape
                }
                ExpressiveHorizontalListItem(
                    item,
                    shape = shape,
                    modifier = itemModifier.weight(1f, true)
                )
                if (index != items.lastIndex) {
                    SpacerH(Paddings.ultraSmall)
                }
            }
        } else if (items.size == 1) {
            ExpressiveHorizontalListItem(
                items.first(),
                shape = fullShape,
                modifier = itemModifier.weight(1f, true)
            )
        }
    }
}

@Composable
fun ExpressiveHorizontalListItem(
    item: ExpressiveListItem,
    shape: Shape = fullShape,
    modifier: Modifier = Modifier
) {
    val containerColor = with((item.containerColor ?: colorScheme.surfaceContainerHigh)) {
        if (item.contentColor != null)
            lerp(this, item.contentColor, item.blendy)
        else this
    }
    val contentColor = item.contentColor ?: colorScheme.contentColorFor(containerColor)



    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Column(
            modifier = modifier.clip(shape)
                .background(containerColor)
                .clickable(onClick = item.onClick)
                .padding(
                    horizontal = Paddings.medium,
                    vertical = Paddings.semiMedium
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item.icon?.let {
                Icon(item.icon, null, modifier = Modifier.scale(1.2f))
                SpacerV(Paddings.semiSmall)
            }
            Text(
                item.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.labelLarge
            )
        }
    }

}