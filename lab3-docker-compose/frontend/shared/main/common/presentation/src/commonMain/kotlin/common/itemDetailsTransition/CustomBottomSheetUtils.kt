package itemDetails.ui.bottomSheet

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

enum class SheetValue { Collapsed, Expanded }

fun SheetValue.isExpanded() = this == SheetValue.Expanded

@Composable
fun rememberCustomSheetState(heightPx: Float, key: Any?) = remember(key) {
    getCustomSheetState(heightPx)
}

fun getCustomSheetState(heightPx: Float) = AnchoredDraggableState(
    initialValue = SheetValue.Collapsed,
    anchors = DraggableAnchors {
        SheetValue.Expanded at 0f
        SheetValue.Collapsed at heightPx
    },
)