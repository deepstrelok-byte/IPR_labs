package widgets.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import view.consts.Paddings

@Composable
fun SectionTitle(
    text: String,
    horizontalPadding: Dp = Paddings.listHorizontalPadding,
    modifier: Modifier = Modifier.fillMaxWidth().padding(
        horizontal = horizontalPadding
    )
) {
    Text(
        text,
        fontWeight = FontWeight.Medium,
        style = typography.headlineSmall,
        maxLines = 1,
        autoSize = TextAutoSize.StepBased(maxFontSize = typography.headlineSmall.fontSize),
        modifier = modifier,
        overflow = TextOverflow.Ellipsis
    )
}