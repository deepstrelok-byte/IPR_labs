package myProfile.ui.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import utils.SpacerH
import view.consts.Paddings
import view.theme.colors.CustomColors

@Composable
internal fun VerificationSection(
    isVerified: Boolean,
    organizationName: String?,
    onClick: () -> Unit
) {

    val containerColor = if (isVerified) CustomColors.greenContainer
    else colorScheme.errorContainer

    val contentColor =
        if (isVerified) CustomColors.onGreenContainer else colorScheme.onErrorContainer

    val icon = if (isVerified) Icons.Rounded.Done else null

    val text = organizationName?.let {
        organizationName
    } ?: if (isVerified) "Аккаунт подтверждён" else "Подтвердить аккаунт"

    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = Paddings.semiSmall)
        ) {
            Text(
                text,
                style = typography.bodyMedium,
                maxLines = 1,
                modifier = Modifier.weight(1f, false)
            )

            icon?.let {
                SpacerH(Paddings.small)
                Icon(icon, null)
            }
        }
    }
}