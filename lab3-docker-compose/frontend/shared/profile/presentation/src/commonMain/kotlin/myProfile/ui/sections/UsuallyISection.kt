package myProfile.ui.sections

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import widgets.sections.SectionTitle
import widgets.expressiveList.ExpressiveListItem
import widgets.expressiveList.cardButton.CardButtonDefaults
import widgets.expressiveList.cardButton.ToggleCardButton

@Composable
internal fun UsuallyISection(
    isHelper: Boolean,
    onClick: (Boolean) -> Unit
) {
    val connectionCorner = CardButtonDefaults.connectionShape.topStart
    val roundedCorner = CardButtonDefaults.shape.topStart


    SectionTitle("Обычно Я...", 0.dp)
    SpacerV(Paddings.small)
    Row(Modifier.height(IntrinsicSize.Max)) {
        ToggleCardButton(
            checked = !isHelper,
            modifier = Modifier.weight(1f).fillMaxHeight(),
            item = ExpressiveListItem(
                icon = Icons.Rounded.Interests,
                text = "Ищу\nпомощь",
                onClick = { onClick(false)  }
            ),
            textMaxLines = 2,
            shape = RoundedCornerShape(
                topStart = roundedCorner,
                bottomStart = roundedCorner,
                topEnd = connectionCorner,
                bottomEnd = connectionCorner
            )
        )
        SpacerH(Paddings.ultraSmall)
        ToggleCardButton(
            checked = isHelper,
            modifier = Modifier.weight(1f).fillMaxHeight(),
            item = ExpressiveListItem(
                icon = Icons.Rounded.VolunteerActivism,
                text = "Помогаю\nнуждающимся",
                onClick = { onClick(true)  }
            ),
            textMaxLines = 2,
            shape = RoundedCornerShape(
                topStart = connectionCorner,
                bottomStart = connectionCorner,
                topEnd = roundedCorner,
                bottomEnd = roundedCorner
            )
        )
    }
}