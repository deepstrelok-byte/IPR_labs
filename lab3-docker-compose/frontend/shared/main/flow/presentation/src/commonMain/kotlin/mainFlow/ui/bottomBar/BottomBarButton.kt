package mainFlow.ui.bottomBar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import dev.chrisbanes.haze.HazeState
import foundation.shapeByInteraction
import utils.defaultMarquee
import view.consts.Paddings
import widgets.glass.GlassCard
import widgets.glass.GlassCardFunctions

@Composable
fun BottomBarButton(
    isSelected: Boolean,
    selectedColor: Color,
    text: String,
    icon: ImageVector,
    hazeState: HazeState,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val shape by shapeByInteraction(isPressed = isPressed, isSelected = isSelected)

    val colorAnimationSpec = spring<Color>(stiffness = Spring.StiffnessMedium)

    val isDarkTheme = isSystemInDarkTheme()

    val color by animateColorAsState(
        (
                if (isSelected || isPressed) selectedColor
                else Color.Transparent)
            .copy(alpha = (if (isDarkTheme) .7f else .45f) + if (isPressed) .2f else 0f),
        animationSpec = colorAnimationSpec
    )

    val hazeTint by animateColorAsState(
        GlassCardFunctions.getHazeTintColor(
            tint = if (isSelected || isPressed) color else null,
            containerColor = colorScheme.primaryContainer,
            containerColorAlpha = .2f
        ),
        animationSpec = colorAnimationSpec
    )
    val contentColor by animateColorAsState(
        if (isPressed) Color.White
        else colorScheme.onBackground,
        animationSpec = colorAnimationSpec
    )
    val borderColor by animateColorAsState(
        GlassCardFunctions.getBorderColor(
            tint = if (isSelected || isPressed) color else null,
            tintColorAlpha = .4f,
            containerColor = colorScheme.primaryContainer,
            containerColorAlpha = .1f
        ),
        animationSpec = colorAnimationSpec
    )


    GlassCard(
        hazeState = hazeState,
        tint = color, //never mind, we overwrote every color
        hazeTint = hazeTint,
        contentColor = contentColor,
        borderColor = borderColor,
        shape = RoundedCornerShape(shape),
        modifier = Modifier
            .combinedClickable(
                interactionSource = interactionSource,
                role = Role.Tab,
                onClick = {
                    onClick()
                },
                onLongClick = if (isSelected) {
                    onLongClick
                } else null,
                hapticFeedbackEnabled = true
            ),
        contentPadding = PaddingValues(
            vertical = Paddings.small,
            horizontal = Paddings.semiMedium
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null)
            Text(
                text,
                textAlign = TextAlign.Center,
                style = typography.labelMedium,
                modifier = Modifier.defaultMarquee()
            )
        }
    }


}