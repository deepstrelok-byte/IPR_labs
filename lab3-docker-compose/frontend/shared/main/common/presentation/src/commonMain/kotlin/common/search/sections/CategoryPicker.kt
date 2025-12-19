package common.search.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import logic.enums.ItemCategory
import utils.SpacerH
import utils.title
import view.consts.Paddings
import widgets.Chip

@Composable
fun CategoryPicker(
    modifier: Modifier = Modifier,
    pickedCategory: ItemCategory?,
    allCategories: List<ItemCategory> = ItemCategory.entries,
    onOtherClick: (() -> Unit)? = null,
    onClick: (ItemCategory) -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.spacedBy(
            Paddings.ultraUltraSmall,
            alignment = Alignment.Top
        )
    ) {
        onOtherClick?.let {
            Row {
                Chip(selected = pickedCategory == null, name = "Любая") {
                    onOtherClick()
                }
                SpacerH(Paddings.small)
            }
        }
        allCategories.forEachIndexed { index, category ->
            Row {
                Chip(
                    selected = category == pickedCategory,
                    name = category.title
                ) {
                    onClick(category)
                }
            }
            if (index != allCategories.lastIndex) {
                SpacerH(Paddings.small)
            }
        }
    }
}