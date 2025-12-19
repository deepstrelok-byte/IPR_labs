package dialogs.verification.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import animations.NetworkButtonIconAnimation
import foundation.DefaultDialog
import utils.SpacerH
import utils.SpacerV
import dialogs.verification.components.VerificationComponent
import view.consts.Paddings
import widgets.textField.SurfaceTextField

@Composable
fun VerificationUI(
    component: VerificationComponent
) {
    val updateResult by component.updateResult.collectAsState()

    val isVerified by component.isVerified.collectAsState()

    val smthChanged =
        component.initialIsVerified != isVerified || component.organizationName.text.toString() != (component.initialOrganizationName ?: "")

    DefaultDialog(
        dismissable = !updateResult.isLoading(),
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
            Text(
                "Подтвердите свой аккаунт через ЕСИА:\nЭто необходимо, чтобы предотвратить случаи мошенничества\n\nНо т.к. это MVP здесь находятся просто настройки верификации для тестирования",
                modifier = Modifier.fillMaxWidth(),
                style = typography.bodySmall,
                textAlign = TextAlign.Center
            )

            SpacerV(Paddings.small)

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Верифицирован", modifier = Modifier.weight(1f, false))
                Switch(
                    checked = isVerified,
                    onCheckedChange = { component.onVerifiedChange(it) }
                )
            }

            SpacerV(Paddings.small)
            SurfaceTextField(
                state = component.organizationName,
                placeholderText = "Название организации"
            )


            SpacerV(Paddings.semiMedium)
            Button(
                onClick = { component.updateVerification() },
                enabled = smthChanged && (component.organizationName.text.isBlank()  || isVerified)
            ) {
                Text("Сохранить", maxLines = 1, overflow = TextOverflow.Ellipsis)
                SpacerH(Paddings.small)
                NetworkButtonIconAnimation(
                    icon = Icons.Rounded.Save,
                    isLoading = updateResult.isLoading()
                )
            }
        }
    }
}