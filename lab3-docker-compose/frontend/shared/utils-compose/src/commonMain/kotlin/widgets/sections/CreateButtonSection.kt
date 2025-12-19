package widgets.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import animations.NetworkButtonIconAnimation
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings

@Composable
fun CreateButtonSection(
    enabled: Boolean,
    isLoading: Boolean,
    text: String,
    icon: ImageVector = Icons.Rounded.FileUpload,
    showAnimation: Boolean = true,
    haveToText: String = "Небходимо заполнить все поля",
    modifier: Modifier = Modifier.fillMaxWidth().padding(horizontal = Paddings.listHorizontalPadding),
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            enabled = enabled
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                NetworkButtonIconAnimation(icon, isLoading)
                SpacerH(Paddings.small)
                Text(text, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
        SpacerV(Paddings.small)
        AnimatedVisibility(!enabled && showAnimation) {
            AnimatedContent(haveToText, transitionSpec = {
                fadeIn().togetherWith(fadeOut())
            }) { text ->
                Text(
                    text,
                    style = typography.bodyMedium,
                    color = colorScheme.onBackground.copy(alpha = .8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}