package compose.alerts

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
internal fun AlertsUI() {
    val alertState by AlertsManager.alertState.collectAsState()

    Crossfade(alertState) { alert ->
        when (alert) {
            AlertState.Hidden -> {}
            is AlertState.SnackBar -> AlertSnackBar(alert)
            is AlertState.SuccessDialog -> SuccessDialog(alert)
        }
    }
}