package auth.ui

import androidx.compose.foundation.text.input.insert
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import animations.NetworkButtonIconAnimation
import auth.components.AuthComponent
import auth.utils.CLEAR_PHONE_REGEX_PATTERN
import auth.utils.clearedPhoneNumber
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import widgets.textField.SurfaceTextField

@Composable
internal fun PhoneProgressStateUI(
    component: AuthComponent
) {

    val requestCodeResult by component.requestCodeResult.collectAsState()

    SurfaceTextField(
        state = component.phoneNumber,
        placeholderText = "Номер телефона",
        icon = Icons.Rounded.Phone,
        singleLine = true,
        keyboardType = KeyboardType.Phone,
        inputTransformation = {
            val text = asCharSequence()
            if (text.length == 13) revertAllChanges()
            else if (text.isNotBlank() && text.first() != '+') {
                this.insert(0, "+")
            }
        },
        readOnly = requestCodeResult.isLoading()
    )
    SpacerV(Paddings.small)

    Text(
        "На указанный номер будет отправлено SMS с кодом подтверждения.",
        style = typography.bodyMedium,
        color = colorScheme.onBackground.copy(alpha = .8f),
        textAlign = TextAlign.Center
    )

    SpacerV(Paddings.big)
    Button(
        enabled = CLEAR_PHONE_REGEX_PATTERN
            .matches(
                component.phoneNumber.text.toString().clearedPhoneNumber()
            ),
        onClick = {
            component.onSendCodeClick()
        }
    ) {
        Text("Отправить код")
        SpacerH(Paddings.semiSmall)
        NetworkButtonIconAnimation(icon = Icons.AutoMirrored.Rounded.Send, isLoading = requestCodeResult.isLoading())
    }

    // TODO: показывать ошибку

}