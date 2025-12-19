package dialogs.editProfile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import animations.NetworkButtonIconAnimation
import dialogs.editProfile.components.EditProfileComponent
import foundation.DefaultDialog
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import widgets.textField.SurfaceTextField

@Composable
fun EditProfileUI(
    component: EditProfileComponent
) {
    val editResult by component.editResult.collectAsState()

    val smthChanged = component.initialName != component.name.text.toString()

    DefaultDialog(
        dismissable = !editResult.isLoading(),
        onDismissRequest = component.onBackClick
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = Paddings.semiMedium, bottom = Paddings.medium)
                .padding(horizontal = Paddings.medium)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SurfaceTextField(
                state = component.name,
                placeholderText = "Имя",
                inputTransformation = {
                    val text = this.asCharSequence()

                    if (text.any { !it.isLetter() && it != ' ' } || text.length > 20) revertAllChanges()
                    else if (text.isNotEmpty()) {
                        val firstChar = text.first()
                        if (firstChar == ' ') revertAllChanges()
                        else replace(0, 1, firstChar.uppercase())
                    }
                }
            )
            SpacerV(Paddings.small)
            Button(
                onClick = { component.editProfile() },
                enabled = smthChanged && component.name.text.isNotEmpty()
            ) {
                Text("Сохранить", maxLines = 1, overflow = TextOverflow.Ellipsis)
                SpacerH(Paddings.small)
                NetworkButtonIconAnimation(
                    icon = Icons.Rounded.Save,
                    isLoading = editResult.isLoading()
                )
            }
        }
    }
}