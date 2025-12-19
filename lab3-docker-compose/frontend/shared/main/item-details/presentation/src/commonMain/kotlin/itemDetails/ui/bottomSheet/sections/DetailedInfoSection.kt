package itemDetails.ui.bottomSheet.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import logic.enums.DeliveryType
import utils.SpacerV
import view.consts.Paddings
import widgets.DeliveryTypesPicker
import widgets.sections.SmallSectionTitle

@Composable
fun DetailedInfoSection(
    deliveryTypes: List<DeliveryType>,
    description: String
) {

    SmallSectionTitle(
        text = "Способы доставки",
        modifier = Modifier.fillMaxWidth().padding(start = Paddings.ultraSmall)
    )
    SpacerV(Paddings.ultraSmall)
    DeliveryTypesPicker(
        pickedDeliveryTypes = listOf(),
        allDeliveryTypes = deliveryTypes,
        modifier = Modifier.fillMaxWidth(),
        isTransparent = true
    ) {}
    SpacerV(Paddings.semiMedium)

    HorizontalDivider(modifier = Modifier.fillMaxWidth(.6f), thickness = 1.dp)
    SpacerV(Paddings.semiMedium)
    Text(
        description,
        modifier = Modifier.fillMaxWidth()
            .padding(start = Paddings.ultraSmall)
    )
}