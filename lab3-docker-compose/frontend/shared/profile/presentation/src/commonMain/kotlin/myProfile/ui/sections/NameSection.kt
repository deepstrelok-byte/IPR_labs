package myProfile.ui.sections

import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
internal fun NameSection(name: String) {
    Text(
        name,
        fontWeight = FontWeight.Medium,
        style = typography.headlineLargeEmphasized
    )
}