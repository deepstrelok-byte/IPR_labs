package registration.components

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface RegistrationComponent {
    val name: TextFieldState
    val telegram: TextFieldState

    val registrationResult: StateFlow<NetworkState<Unit>>

    fun onRegistrationClick()


}