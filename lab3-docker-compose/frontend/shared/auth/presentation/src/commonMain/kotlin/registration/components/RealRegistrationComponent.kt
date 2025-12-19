package registration.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.AuthUseCases
import usecases.extra.RegisterUseCase

class RealRegistrationComponent(
    componentContext: ComponentContext,
    val goMain: () -> Unit
) : RegistrationComponent, KoinComponent, ComponentContext by componentContext {

    val coroutineScope = componentCoroutineScope()

    val authUseCases: AuthUseCases = get()
    val registerUseCase: RegisterUseCase = get()

    override val name: TextFieldState = TextFieldState()
    override val telegram: TextFieldState = TextFieldState()
    override val registrationResult  =
        MutableStateFlow<NetworkState<Unit>>(NetworkState.AFK)


    override fun onRegistrationClick() {
        if (!registrationResult.value.isLoading()) {
            coroutineScope.launchIO {
                registerUseCase(
                    name = name.text.toString(),
                    telegram = telegram.text.toString()
                ).collect {
                    registrationResult.value = it
                }

                withContext(Dispatchers.Main) {
                    registrationResult.value.handle(
                        onError = { AlertsManager.push(AlertState.SnackBar(it.prettyPrint)) }
                    ) {

                        goMain()
                    }
                }
            }.invokeOnCompletion {
                registrationResult.value = registrationResult.value.onCoroutineDeath()
            }
        }
    }
}