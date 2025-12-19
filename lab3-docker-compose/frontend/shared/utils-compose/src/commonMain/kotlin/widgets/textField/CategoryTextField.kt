package widgets.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import logic.enums.ItemCategory
import utils.title
import view.consts.Paddings

@Composable
fun CategoryTextField(
    itemCategory: ItemCategory?,
    expandable: Boolean = false,
    modifier: Modifier = Modifier.padding(horizontal = Paddings.listHorizontalPadding),
    onClick: (ItemCategory) -> Unit
) {
    val state = TextFieldState(itemCategory?.title ?: "")
    var expanded by remember { mutableStateOf(false) }


    // rounded corners for dropdownmenu
    MaterialExpressiveTheme(
        shapes = shapes.copy(extraSmall = shapes.extraLarge)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it && expandable
            },
            modifier = modifier
        ) {
            SurfaceTextField(
                state = state,
                placeholderText = "Категория",
                modifier = Modifier.fillMaxWidth(),
                textStyle = typography.bodyLarge,
                imeAction = ImeAction.Done,
                readOnly = true,
                textFieldModifier = SurfaceTextFieldDefaults.textFieldModifier.menuAnchor(
                    ExposedDropdownMenuAnchorType.PrimaryNotEditable
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                ItemCategory.entries.forEach { itemCategory ->
                    DropdownMenuItem(
                        text = { Text(itemCategory.title) },
                        onClick = {
                            onClick(itemCategory)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}