package requestDetails.ui.sections

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import logic.enums.DeliveryType
import utils.SpacerV
import view.consts.Paddings
import widgets.DeliveryTypesPicker
import widgets.DeliveryTypesPickerDefaults

@Composable
internal fun DeliveryTypesSection(
    deliveryTypes: List<DeliveryType>,
    isEditable: Boolean,
    updateDeliveryType: (DeliveryType) -> Unit
) {
    Text(
        "Способы доставки",
        modifier = Modifier.padding(start = Paddings.medium),
        fontWeight = FontWeight.Medium
    )
    SpacerV(Paddings.semiSmall)
    DeliveryTypesPicker(
        pickedDeliveryTypes = if (isEditable) deliveryTypes else listOf(), modifier = Modifier.horizontalScroll(
            rememberScrollState()
        ),
        initSpacer = Paddings.medium,
        allDeliveryTypes = if (isEditable) DeliveryTypesPickerDefaults.allDeliveryTypes else deliveryTypes
    ) {
        updateDeliveryType(it)
    }
}