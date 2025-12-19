package common

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ReportGmailerrorred
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import common.CreatorInfoSectionStates.Errored
import common.CreatorInfoSectionStates.Loading
import common.CreatorInfoSectionStates.Ok
import utils.SpacerH
import utils.SpacerV
import view.consts.Paddings
import widgets.Avatar
import widgets.SimpleChip
import widgets.sections.SmallSectionTitle


private enum class CreatorInfoSectionStates {
    Loading, Errored, Ok
}

@Composable
fun UserInfoSection(
    name: String,
    given: Int,
    taken: Int,
    isVerified: Boolean,
    organizationName: String?,
    isMe: Boolean,
    isOwner: Boolean,
    smallSectionTitlePadding: PaddingValues = PaddingValues(0.dp),
    isLoading: Boolean,
    error: String?,
    onErrorClick: () -> Unit,
    onProfileClick: () -> Unit,
    onReportClick: () -> Unit
) {

    val state =
        if (error != null) Errored else if (isLoading) Loading else Ok

    SmallSectionTitle(
        text = if (isOwner) "Получатель" else "Даритель",
        modifier = Modifier.fillMaxWidth().padding(smallSectionTitlePadding)
    )
    Row(
        Modifier.height(IntrinsicSize.Max).fillMaxWidth()
            .clip(shapes.extraLarge)
            .background(colorScheme.primaryContainer.copy(alpha = .2f))
            .clickable(enabled = state == Ok, onClick = onProfileClick)
            .padding(Paddings.small)
    ) {

        AnimatedContent(state) { animState ->
            when (animState) {
                Loading -> Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) { LoadingIndicator() }

                Errored -> Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        error ?: "",
                        textAlign = TextAlign.Center,
                    )
                    Button(onClick = onErrorClick) {
                        Text("Ещё раз")
                    }
                }

                Ok -> Row {
                    Avatar(
                        modifier = Modifier.fillMaxHeight(),
                        iconPadding = Paddings.small
                    )
                    SpacerH(Paddings.semiMedium)
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                name,
                                fontWeight = FontWeight.Medium,
                                style = typography.headlineSmallEmphasized,
                                lineHeight = typography.headlineSmallEmphasized.fontSize
                            )
                            SpacerH(Paddings.small)

                            if (isVerified) {
                                SimpleChip(organizationName ?: "Подтверждён")
                            }
                        }
                        SpacerV(Paddings.semiSmall)
                        Text(
                            "Передано: $given Получено: $taken",
                            style = typography.bodyMedium,
                            lineHeight = typography.bodyMedium.fontSize,
                            modifier = Modifier.horizontalScroll(rememberScrollState())
                        )
                    }
                }

            }
        }
    }
    if (!isMe && state == Ok) {
        SpacerV(Paddings.small)
        TextButton(
            onClick = onReportClick,
            colors = ButtonDefaults.textButtonColors(
                containerColor = colorScheme.errorContainer.copy(
                    alpha = .6f
                ),
                contentColor = colorScheme.onErrorContainer
            )
        ) {
            Icon(Icons.Rounded.ReportGmailerrorred, null)
            SpacerH(Paddings.semiSmall)
            Text("Пожаловаться")
        }
    }


}