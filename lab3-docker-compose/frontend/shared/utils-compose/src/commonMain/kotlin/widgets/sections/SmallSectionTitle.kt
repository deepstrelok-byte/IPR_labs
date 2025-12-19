package widgets.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun SmallSectionTitle(
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Text(
        text,
        style = typography.bodyLarge,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}