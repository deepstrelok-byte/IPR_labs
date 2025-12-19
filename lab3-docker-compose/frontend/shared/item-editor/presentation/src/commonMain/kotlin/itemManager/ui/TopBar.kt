package itemManager.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import view.consts.Paddings
import widgets.glass.BackGlassButton

@Composable
internal fun TopBar(
    isVisible: Boolean,
    topPadding: Dp,
    hazeState: HazeState,
    isEditing: Boolean,
    onBackClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth().padding(top = topPadding + Paddings.small)
            .padding(horizontal = Paddings.listHorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackGlassButton(hazeState = hazeState) {
            onBackClick()
        }
        AnimatedVisibility(
            isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = if (isEditing) "Редактировать" else "Подарить вещь",
                fontWeight = FontWeight.SemiBold,
                style = typography.headlineMedium,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(maxFontSize = typography.headlineMedium.fontSize)
            )
        }
        Box(Modifier.size(24.dp + Paddings.semiMedium))
    }
}