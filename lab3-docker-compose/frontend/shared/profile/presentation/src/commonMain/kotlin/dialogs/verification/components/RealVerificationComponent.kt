package dialogs.verification.components

import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import dialogs.interfaces.DialogComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.UserUseCases

class RealVerificationComponent(
    componentContext: ComponentContext,
    override val onBackClick: () -> Unit,
    override val initialIsVerified: Boolean,
    override val initialOrganizationName: String?,
    private val onIsVerifiedChanged: (Boolean) -> Unit,
    private val onOrganizationNameChanged: (String?) -> Unit,

    ) : VerificationComponent, DialogComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val userUseCases: UserUseCases = get()

    override val isVerified: MutableStateFlow<Boolean> =
        MutableStateFlow(initialIsVerified)

    override fun onVerifiedChange(isVerified: Boolean) {
        this.isVerified.value = isVerified
    }

    override val organizationName: TextFieldState = TextFieldState(initialOrganizationName ?: "")
    override val updateResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)

    override fun updateVerification() {
        if (!updateResult.value.isLoading()) {
            coroutineScope.launchIO {
                val organizationName = organizationName.text.toString().ifBlank { null }
                userUseCases.changeVerification(isVerified = isVerified.value, organizationName = organizationName).collect {
                    updateResult.value = it
                    with(Dispatchers.Main) {
                        it.handle {
                            onIsVerifiedChanged(isVerified.value)
                            onOrganizationNameChanged(organizationName)
                            onBackClick()
                        }
                    }
                }
            }.invokeOnCompletion {
                updateResult.value = updateResult.value.onCoroutineDeath()
            }
        }
    }

}