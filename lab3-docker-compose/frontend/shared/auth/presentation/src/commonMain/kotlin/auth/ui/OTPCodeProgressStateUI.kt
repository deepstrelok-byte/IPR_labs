package auth.ui

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Message
import androidx.compose.material.icons.automirrored.rounded.NavigateBefore
import androidx.compose.material.icons.automirrored.rounded.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import animations.NetworkButtonIconAnimation
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import widgets.textField.SurfaceTextField


@Composable
internal fun OTPCodeProgressStateUI(
    OTPCode: TextFieldState,
    isLoading: Boolean,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {



    SurfaceTextField(
        state = OTPCode,
        placeholderText = "Код",
        icon = Icons.AutoMirrored.Rounded.Message,
        singleLine = true,
        keyboardType = KeyboardType.NumberPassword,
        inputTransformation = {
            val text = asCharSequence()
            val shouldRevert =
                text.length > 4 || text.any { !it.isDigit() }

            if (shouldRevert) revertAllChanges()
        }
    )
    SpacerV(Paddings.small)

    Text(
        "Не пришёл код? Введите \"1111\" (MVP)",
        style = typography.bodyMedium,
        color = colorScheme.onBackground.copy(alpha = .8f),
        textAlign = TextAlign.Center
    )

    SpacerV(Paddings.big)
    SplitButtonLayout(
        leadingButton = {
            SplitButtonDefaults.LeadingButton(
                onClick = {
                    onBackClick()
                }
            ) {
                Icon(Icons.AutoMirrored.Rounded.NavigateBefore, null)
            }
        },
        trailingButton = {
            SplitButtonDefaults.TrailingButton(
                onClick = {
                    onNextClick()
                },
                enabled = OTPCode.text.length == 4
                        && OTPCode.text.all { it.isDigit() }
            ) {
                SpacerH(Paddings.semiSmall)
                Text("Далее")
                SpacerH(Paddings.semiSmall)
                NetworkButtonIconAnimation(
                    icon = Icons.AutoMirrored.Rounded.NavigateNext,
                    isLoading = isLoading
                )
            }
        }
    )
}