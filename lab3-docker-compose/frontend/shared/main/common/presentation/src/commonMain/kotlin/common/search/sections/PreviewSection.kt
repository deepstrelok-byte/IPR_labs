package common.search.sections

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import foundation.layouts.RightImportantLayout
import widgets.Chip
import widgets.ChipDefaults

@Composable
internal fun PreviewSection(
    location: String,
    isExpanded: Boolean,
    countOfActiveFilters: Int,
    onFilterButtonClick: () -> Unit
) {
    val density = LocalDensity.current

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RightImportantLayout(
            modifier = Modifier.fillMaxWidth(),
            leftSide = {
                Chip(
                    selected = true,
                    name = location,
                    trailingIcon = {
                        Icon(Icons.Rounded.ArrowDropDown, null)
                    },
                    textModifier = ChipDefaults.textModifier.basicMarquee(),
                ) { AlertsManager.push(AlertState.SnackBar("MVP moment")) }
            },
            rightSide = {
                Box() {
                    var maxWidth by remember { mutableStateOf(0.dp) }

                    Chip(
                        selected = isExpanded, name = if (isExpanded) "Скрыть" else "Фильтры",
                        modifier = Modifier,
                        textModifier = ChipDefaults.textModifier.then(
                            if (maxWidth > 0.dp) Modifier.width(maxWidth)
                            else Modifier
                        ).onSizeChanged {
                            with(density) {
                                if (maxWidth.roundToPx() < it.width) maxWidth = it.width.toDp()
                            }
                        }
                    ) {
                        onFilterButtonClick()
                    }
                    if (countOfActiveFilters > 0 && !isExpanded) {
                        Badge(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .height(with(density) { typography.labelSmall.lineHeight.toDp() })
                                .aspectRatio(1f, true)
                        ) {
                            Text(countOfActiveFilters.toString())
                        }
                    }
                }
            },
            rightSideMaxPart = .6f
        )

    }
}