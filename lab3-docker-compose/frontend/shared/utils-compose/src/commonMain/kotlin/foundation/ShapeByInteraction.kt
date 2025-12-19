package foundation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import foundation.ShapeByInteractionDefaults.extraLargeDpShape
import foundation.ShapeByInteractionDefaults.mediumDpShape
import foundation.ShapeByInteractionDefaults.shapeAnimationSpec


object ShapeByInteractionDefaults {
    val mediumDpShape = 12.dp
    val largeIncreasedDpShape = 20.dp
    val extraLargeDpShape = 28.dp

    val shapeAnimationSpec = spring<Dp>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
}

@Composable
fun shapeByInteraction(
    isPressed: Boolean,
    shapeDp: Dp = extraLargeDpShape,
    pressedShapeDp: Dp = mediumDpShape,
): State<Dp> {
    return animateDpAsState(
        if (isPressed) pressedShapeDp
        else shapeDp,
        animationSpec = shapeAnimationSpec
    )

}

@Composable
fun shapeByInteraction(
    isPressed: Boolean,
    isSelected: Boolean,
    shapeDp: Dp = extraLargeDpShape,
    selectedShapeDp: Dp = ShapeByInteractionDefaults.largeIncreasedDpShape,
    pressedShapeDp: Dp = mediumDpShape,
): State<Dp> {
    return animateDpAsState(
        if (isPressed) pressedShapeDp
        else if (isSelected) selectedShapeDp
        else shapeDp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

}