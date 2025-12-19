package alertsManager

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface AlertState {
    data object Hidden : AlertState
    data class SnackBar(
        val message: String,
        val bottomExtraPadding: Dp = 0.dp
    ) : AlertState

    data class SuccessDialog(
        val message: String
    ) : AlertState
}
