package itemDetails.ui.bottomSheet.sections

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import itemDetails.ui.bottomSheet.SheetValue
import logic.enums.ItemCategory
import utils.title
import view.consts.Paddings
import widgets.sections.LocationSection

@Composable
fun QuickInfoSection(
    title: String,
    category: ItemCategory,
    location: String,
    sheetState: AnchoredDraggableState<SheetValue>
) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = typography.headlineLargeEmphasized,
        modifier = Modifier
            .padding(horizontal = Paddings.medium).fillMaxWidth().anchoredDraggable(
                sheetState,
                orientation = Orientation.Vertical
            ),
        fontWeight = FontWeight.Medium
    )
    Text(
        category.title,
        style = typography.labelMedium,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.alpha(.7f)
    )
    LocationSection(modifier = Modifier.fillMaxWidth(), location = location)


}