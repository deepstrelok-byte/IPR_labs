package auth.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases

class RealAuthComponent(
    componentContext: ComponentContext,
    private val output: (AuthComponent.Output) -> Unit
) : AuthComponent, KoinComponent, ComponentContext by componentContext {


    private val authUseCases: AuthUseCases = this.get()

    private val coroutineScope =
        componentCoroutineScope()

    override val phoneNumber: TextFieldState = TextFieldState("+7")
    override val OTPCode: TextFieldState = TextFieldState()

    override val currentProgressState = MutableStateFlow(AuthProgressState.PHONE)


    override val requestCodeResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)

    override val verifyCodeResult: MutableStateFlow<NetworkState<Boolean>> =
        MutableStateFlow(NetworkState.AFK)


    @OptIn(InternalCoroutinesApi::class)
    override fun onSendCodeClick() {
        if (!requestCodeResult.value.isLoading()) {
            coroutineScope.launchIO {
                authUseCases.requestCode(phoneNumber.text.toString()).collect {
                    requestCodeResult.value = it
                }

                requestCodeResult.value.handle(
                    onError = { AlertsManager.push(AlertState.SnackBar(it.prettyPrint)) }
                ) {

                    currentProgressState.value = AuthProgressState.OTPCode
                }
            }.invokeOnCompletion {
                requestCodeResult.value = requestCodeResult.value.onCoroutineDeath()
            }
        }
    }

    override fun onVerifyCodeClick() {
        if (!verifyCodeResult.value.isLoading()) {
            coroutineScope.launchIO {
                authUseCases.verifyCode(
                    phone = phoneNumber.text.toString(),
                    otp = OTPCode.text.toString()
                ).collect {
                    verifyCodeResult.value = it
                }
                verifyCodeResult.value.onError {
                    AlertsManager.push(AlertState.SnackBar(it.prettyPrint))
                }

                withContext(Dispatchers.Main) {
                    verifyCodeResult.value.handle { result ->
                        if (result.data) { // have to register
                            output(AuthComponent.Output.NavigateToRegistration)
                        } else {
                            // already registered -> go to main
                            output(AuthComponent.Output.NavigateToMain)
                        }
                    }
                }
            }.invokeOnCompletion {
                verifyCodeResult.value = verifyCodeResult.value.onCoroutineDeath()
            }
        }
    }

    override fun onBackClick() {
        when (currentProgressState.value) {
            AuthProgressState.PHONE -> {}
            AuthProgressState.OTPCode -> {
                this.currentProgressState.value = AuthProgressState.PHONE
            }
        }
    }
}