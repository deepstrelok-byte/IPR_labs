package widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import view.consts.Paddings

@Composable
fun Avatar(
    modifier: Modifier = Modifier
        .sizeIn(maxWidth = 200.dp),
    iconPadding: Dp = Paddings.big
) {

    Box(
        modifier.aspectRatio(1f).clip(CircleShape)
            .background(colorScheme.tertiaryContainer.copy(alpha = .4f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Rounded.Person,
            modifier = Modifier.padding(iconPadding)
                .matchParentSize().alpha(.8f),
            contentDescription = null
        )

    }
}