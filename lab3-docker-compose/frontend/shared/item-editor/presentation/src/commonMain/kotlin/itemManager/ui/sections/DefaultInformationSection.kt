package itemManager.ui.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import logic.enums.ItemCategory
import utils.SpacerV
import view.consts.Paddings
import widgets.sections.SectionTitle
import widgets.textField.CategoryTextField
import widgets.textField.SurfaceTextField
import widgets.textField.SurfaceTextFieldDefaults


@Composable
internal fun DefaultInformationSection(
    titleState: TextFieldState,
    descState: TextFieldState,
    itemCategory: ItemCategory?,
    readOnly: Boolean,
    preCategory: ItemCategory?,
    onItemCategoryClick: (ItemCategory) -> Unit
) {
    SectionTitle("Информация")

    SpacerV(Paddings.small)
    DefaultInformationTextField(
        placeholderText = "Название",
        state = titleState,
        imeAction = ImeAction.Next,
        readOnly = readOnly
    )

    SpacerV(Paddings.small)
    DefaultInformationTextField(
        placeholderText = "Описание",
        state = descState,
        imeAction = ImeAction.Done,
        readOnly = readOnly
    )
    SpacerV(Paddings.small)

    CategoryTextField(preCategory ?: itemCategory, expandable = preCategory == null && !readOnly) {
        onItemCategoryClick(it)
    }

}

@Composable
private fun DefaultInformationTextField(
    paddingValues: PaddingValues = PaddingValues(horizontal = Paddings.listHorizontalPadding),
    placeholderText: String,
    imeAction: ImeAction,
    state: TextFieldState,
    readOnly: Boolean = false,
    textFieldModifier: Modifier = SurfaceTextFieldDefaults.textFieldModifier
) {
    SurfaceTextField(
        state = state,
        placeholderText = placeholderText,
        modifier = Modifier.fillMaxWidth(),
        paddings = paddingValues,
        textStyle = typography.bodyLarge,
        imeAction = imeAction,
        readOnly = readOnly,
        textFieldModifier = textFieldModifier
    )
}