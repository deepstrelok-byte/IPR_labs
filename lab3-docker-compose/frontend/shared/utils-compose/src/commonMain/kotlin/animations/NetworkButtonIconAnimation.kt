package animations

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import view.consts.Sizes

@Composable
fun NetworkButtonIconAnimation(
    icon: ImageVector,
    isLoading: Boolean
) {
    Crossfade(!isLoading) { isIcon ->
        if (isIcon)
            Icon(icon, null)
        else
            CircularProgressIndicator(
                modifier = Modifier.size(Sizes.iconSize),
                color = LocalContentColor.current
            )

    }
}