package widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import logic.enums.DeliveryType
import utils.SpacerH
import utils.title
import view.consts.Paddings

data object DeliveryTypesPickerDefaults {
    val allDeliveryTypes = DeliveryType.entries.toList()
}

@Composable
fun DeliveryTypesPicker(
    pickedDeliveryTypes: List<DeliveryType>,
    allDeliveryTypes: List<DeliveryType> = DeliveryTypesPickerDefaults.allDeliveryTypes,
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(horizontal = Paddings.listHorizontalPadding),
    isTransparent: Boolean = false,
    onOtherClick: (() -> Unit)? = null,
    initSpacer: Dp = 0.dp,
    onClick: (DeliveryType) -> Unit
) {

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.spacedBy(
            Paddings.ultraUltraSmall,
            alignment = Alignment.Top
        )
    ) {
        SpacerH(initSpacer)
        onOtherClick?.let {
            Row {
                Chip(selected = pickedDeliveryTypes.isEmpty(), name = "Любой", isTransparent = isTransparent) {
                    onOtherClick()
                }
                SpacerH(Paddings.small)
            }
        }
        allDeliveryTypes.forEachIndexed { index, type ->
            Row {
                Chip(
                    selected = type in pickedDeliveryTypes,
                    name = type.title,
                    isTransparent = isTransparent
                ) {
                    onClick(type)
                }
            }
            if (index != allDeliveryTypes.lastIndex) {
                SpacerH(Paddings.small)
            }
        }
    }

    SpacerH(initSpacer)
}


