package widgets.expressiveList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import widgets.expressiveList.ExpressiveVerticalListDefaults.endShape
import widgets.expressiveList.ExpressiveVerticalListDefaults.fullShape
import widgets.expressiveList.ExpressiveVerticalListDefaults.middleShape
import widgets.expressiveList.ExpressiveVerticalListDefaults.startShape

object ExpressiveVerticalListDefaults {
    val connection = 8.dp
    val corner = 16.dp
    val startShape = RoundedCornerShape(
        topStart = corner,
        topEnd = corner,
        bottomStart = connection,
        bottomEnd = connection
    )
    val middleShape = RoundedCornerShape(connection)
    val endShape = RoundedCornerShape(
        bottomStart = corner,
        bottomEnd = corner,
        topStart = connection,
        topEnd = connection
    )

    val fullShape = RoundedCornerShape(corner)
}

@Composable
fun ExpressiveVerticalList(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    items: List<ExpressiveListItem>
) {


    Column(modifier = modifier) {
        items.forEachIndexed { index, item ->
            val shape = when (index) {
                0 -> startShape
                items.lastIndex -> endShape
                else -> middleShape
            }
            ExpressiveVerticalListItem(
                item,
                shape = shape,
                modifier = itemModifier
            )
            if (index != items.lastIndex) {
                SpacerV(Paddings.ultraSmall)
            }
        }
    }
}

@Composable
fun ExpressiveVerticalListItem(
    item: ExpressiveListItem,
    shape: Shape = fullShape,
    modifier: Modifier = Modifier
) {
    val containerColor = item.containerColor ?: colorScheme.surfaceContainerHigh
    val contentColor = item.contentColor ?: colorScheme.contentColorFor(containerColor)

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Row(
            modifier = modifier.clip(shape)
                .background(containerColor)
                .clickable(onClick = item.onClick)
                .padding(
                    horizontal = Paddings.medium,
                    vertical = Paddings.semiMedium
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item.icon?.let {
                Icon(item.icon, null, modifier = Modifier.scale(1.2f))
                SpacerH(Paddings.medium)
            }
            Text(
                item.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyLarge
            )
        }
    }

}