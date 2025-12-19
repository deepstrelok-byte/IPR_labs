package common.itemCard

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import view.consts.Paddings

object ItemCardDefaults {
    val containerPadding = Paddings.small
    val cardShapeDp = 25.dp
    val imageShapeDp = cardShapeDp - containerPadding

    val imageShape = RoundedCornerShape(imageShapeDp)
}