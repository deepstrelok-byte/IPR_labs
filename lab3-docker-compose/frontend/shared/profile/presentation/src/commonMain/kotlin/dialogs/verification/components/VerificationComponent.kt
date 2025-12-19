package dialogs.verification.components

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface VerificationComponent {


    val initialIsVerified: Boolean
    val initialOrganizationName: String?

    val onBackClick: () -> Unit

    val isVerified: StateFlow<Boolean>

    fun onVerifiedChange(isVerified: Boolean)

    val organizationName: TextFieldState

    val updateResult: StateFlow<NetworkState<Unit>>

    fun updateVerification()
}
