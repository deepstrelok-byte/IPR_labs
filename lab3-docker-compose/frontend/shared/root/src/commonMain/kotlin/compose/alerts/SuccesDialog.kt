package compose.alerts

import alertsManager.AlertState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import utils.SpacerV
import view.consts.Paddings
import view.consts.Sizes
import view.theme.colors.CustomColors

@Composable
internal fun SuccessDialog(
    alert: AlertState.SuccessDialog
) {
    Box(
        Modifier.fillMaxSize().background(Color.Black.copy(.3f)),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard {
            Column(
                Modifier.padding(Paddings.big),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    tint = CustomColors.onGreenContainer,
                    modifier = Modifier
                        .size(Sizes.hugeCircularButton)
                        .clip(CircleShape)
                        .background(CustomColors.greenContainer)
                        .padding(Paddings.small),
                    contentDescription = null
                )
                SpacerV(Paddings.small)
                Text(alert.message, textAlign = TextAlign.Center)
            }
        }
    }
}