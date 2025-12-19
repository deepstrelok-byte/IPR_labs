package requestDetails.ui.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import logic.enums.ItemCategory
import utils.SpacerV
import view.consts.Paddings
import widgets.textField.CategoryTextField
import widgets.textField.SurfaceTextField

@Composable
internal fun TextFieldsSection(
    requestTextState: TextFieldState,
    category: ItemCategory?,
    isCreationMode: Boolean,
    isLoading: Boolean,
    updateCategory: (ItemCategory) -> Unit
) {
    SurfaceTextField(
        state = requestTextState,
        placeholderText = "Заявка",
        paddings = PaddingValues(horizontal = Paddings.medium),
        readOnly = !isCreationMode || isLoading
    )
    SpacerV(Paddings.small)
    CategoryTextField(
        category,
        expandable = isCreationMode && !isLoading,
        modifier = Modifier.padding(horizontal = Paddings.medium)
    ) {
        updateCategory(it)
    }
}