package myProfile.ui.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import widgets.expressiveList.ExpressiveListItem
import widgets.expressiveList.ExpressiveVerticalList

@Composable
internal fun ListSection(
    onProfileEditClick: () -> Unit,
    onOperationsClick: () -> Unit,
) {

    val profileEdit = ExpressiveListItem(
        icon = Icons.Rounded.Settings,
        text = "Редактировать профиль",
        onClick = onProfileEditClick
    )
    val operationsHistory = ExpressiveListItem(
        icon = Icons.Rounded.History,
        text = "История операций",
        onClick = onOperationsClick
    )


    ExpressiveVerticalList(
        modifier = Modifier,
        itemModifier = Modifier.fillMaxWidth(),
        items = listOf(
            profileEdit,
            operationsHistory
        )
    )
}