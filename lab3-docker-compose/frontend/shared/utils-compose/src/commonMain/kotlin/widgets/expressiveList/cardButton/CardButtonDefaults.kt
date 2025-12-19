package widgets.expressiveList.cardButton

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object CardButtonDefaults {
    val minCardButtonHeight = 100.dp
    val maxIconSize = 32.dp
    val shape: CornerBasedShape
        @Composable
        get() = shapes.extraLarge

    val connectionShape: CornerBasedShape
        @Composable
        get() = shapes.small
}