package myProfile.ui.sections

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.SpacerV
import utils.getCoolPrimary
import view.consts.Paddings
import view.theme.colors.CustomColors
import widgets.sections.SectionTitle

@Composable
internal fun FontSizeSection(
    value: Float,
    onChange: (Float) -> Unit
) {
    val activeColor = getCoolPrimary()
    val colors = SliderDefaults.colors(
        thumbColor = activeColor,
        activeTrackColor = activeColor,
        activeTickColor = CustomColors.lightPrimary,
        inactiveTrackColor = colorScheme.surfaceContainerHighest,
        inactiveTickColor = colorScheme.onBackground,

    )
    SectionTitle("Размер шрифта", 0.dp)
    SpacerV(Paddings.small)

    Slider(
        value = value,
        onValueChange = onChange,
        valueRange = 0.75f..1.5f,
        steps = 9,
        colors = colors,
        track = { sliderState ->
            SliderDefaults.Track(
                sliderState = sliderState,
                modifier = Modifier.height(25.dp),
                trackCornerSize = 10.dp,
                colors = colors,
                drawStopIndicator = null
            )
        },
    )
}