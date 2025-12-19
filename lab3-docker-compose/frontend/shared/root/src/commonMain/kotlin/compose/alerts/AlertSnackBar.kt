package compose.alerts

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import view.consts.Paddings

@Composable
internal fun AlertSnackBar(
    alert: AlertState.SnackBar
) {
    val safeContentBottomPadding =
        WindowInsets.safeContent.asPaddingValues().calculateBottomPadding()
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

    Box(
        Modifier.fillMaxSize()
            .padding(bottom = alert.bottomExtraPadding + safeContentBottomPadding + Paddings.medium),
        contentAlignment = Alignment.BottomCenter
    ) {
        SwipeToDismissBox(
            state = swipeToDismissBoxState,
            backgroundContent = { },
            modifier = Modifier.fillMaxWidth(),
            onDismiss = {
                AlertsManager.hideNotification()
            }
        ) {
            Snackbar(
                Modifier.padding(horizontal = Paddings.medium)
            ) {
                Text(alert.message)
            }
        }
    }
}