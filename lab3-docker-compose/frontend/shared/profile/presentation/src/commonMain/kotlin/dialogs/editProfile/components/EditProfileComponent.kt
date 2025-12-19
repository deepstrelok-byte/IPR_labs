package dialogs.editProfile.components

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface EditProfileComponent {

    val onBackClick: () -> Unit

    val initialName: String

    val name: TextFieldState

    val editResult: StateFlow<NetworkState<Unit>>

    fun editProfile()
}
