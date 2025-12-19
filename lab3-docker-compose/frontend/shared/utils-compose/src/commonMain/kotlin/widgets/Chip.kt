package widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import view.consts.Paddings

object ChipDefaults {
    val textModifier = Modifier
        .padding(vertical = Paddings.semiSmall)
}


@Composable
fun Chip(
    selected: Boolean,
    name: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
        .padding(vertical = Paddings.semiSmall),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isTransparent: Boolean = false,
    onClick: () -> Unit,
) {
    val containerColor = lerp(
        colorScheme.primaryContainer,
        colorScheme.background,
        .3f
    )
    AnimatedContent(
        selected,
        transitionSpec = {
            fadeIn(animationSpec = tween(220))
                .togetherWith(fadeOut(animationSpec = tween(220)))
        },
    ) { isSelected ->
        FilterChip(
            modifier = modifier,
            selected = isSelected,
            onClick = onClick,
            label = {
                Text(
                    name,
                    modifier = textModifier,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = FilterChipDefaults.elevatedFilterChipColors(
                selectedContainerColor = containerColor,
                selectedLabelColor = colorScheme.onPrimaryContainer,
                containerColor = colorScheme.surfaceContainerLow.copy(alpha = if (isTransparent) .7f else 1f)
            ),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )
    }
}