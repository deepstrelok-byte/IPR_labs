package itemManager.ui.sections

import androidx.compose.runtime.Composable
import logic.enums.DeliveryType
import utils.SpacerV
import view.consts.Paddings
import widgets.DeliveryTypesPicker
import widgets.sections.SectionTitle


@Composable
internal fun DeliveryTypesSection(
    pickedDeliveryTypes: List<DeliveryType>,
    availableDeliveryTypes: List<DeliveryType>,
    onClick: (DeliveryType) -> Unit
) {


    SectionTitle("Способы доставки")
    SpacerV(Paddings.small)
    DeliveryTypesPicker(pickedDeliveryTypes = pickedDeliveryTypes, allDeliveryTypes = availableDeliveryTypes, onClick = onClick)
}

