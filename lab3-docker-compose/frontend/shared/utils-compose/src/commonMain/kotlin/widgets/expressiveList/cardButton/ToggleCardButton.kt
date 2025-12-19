package widgets.expressiveList.cardButton

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import utils.getCoolPrimary
import view.consts.Paddings
import view.theme.colors.CustomColors
import widgets.expressiveList.ExpressiveListItem

@Composable
fun ToggleCardButton(
    checked: Boolean,
    modifier: Modifier = Modifier,
    item: ExpressiveListItem,
    textMaxLines: Int = Int.MAX_VALUE,
    minHeight: Dp = CardButtonDefaults.minCardButtonHeight,
    contentPadding: PaddingValues = PaddingValues(
        vertical = Paddings.semiMedium,
        horizontal = Paddings.small
    ),
    shape: Shape = CardButtonDefaults.shape
) {
    val isDark = isSystemInDarkTheme()


    val containerColor by
    animateColorAsState(
        if (checked) getCoolPrimary() else
            if (isDark) colorScheme.surfaceContainerHigh
            else getCoolPrimary(.9f)
    )

    val contentColor by animateColorAsState(
        if (checked) lerp(
            CustomColors.lightPrimary,
            Color.Black,
            fraction = .3f
        )
        else lerp(colorScheme.primary, colorScheme.onBackground, .7f)
    )

    CardButton(
        modifier = modifier,
        item = item,
        textMaxLines = textMaxLines,
        minHeight = minHeight,
        contentPadding = contentPadding,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor
    )
}