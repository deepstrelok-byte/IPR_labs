package widgets.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import utils.SpacerH
import view.consts.Paddings

@Composable
fun LocationSection(modifier: Modifier = Modifier, location: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Icon(Icons.Rounded.LocationOn, contentDescription = null)
        SpacerH(Paddings.semiSmall)
        Text(text = location, textAlign = TextAlign.Center, style = typography.labelLarge)
    }
}