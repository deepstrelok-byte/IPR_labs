package dialogs.editProfile.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import dialogs.interfaces.DialogComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.extra.RegisterUseCase

class RealEditProfileComponent(
    componentContext: ComponentContext,
    override val initialName: String,
    override val onBackClick: () -> Unit,
    val onNameChange: (String) -> Unit
) : EditProfileComponent, DialogComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val registerUseCase: RegisterUseCase = get() //meowmeow

    override val name: TextFieldState = TextFieldState(initialName)
    override val editResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)

    override fun editProfile() {
        if (!editResult.value.isLoading()) {
            val name = name.text.toString()
            coroutineScope.launchIO {
                registerUseCase(
                    name = name,
                    telegram = "######" // до сдачи проекта -1 день
                ).collect {
                    editResult.value = it
                }

                withContext(Dispatchers.Main) {
                    editResult.value.handle(
                        onError = { AlertsManager.push(AlertState.SnackBar(it.prettyPrint)) }
                    ) {
                        onNameChange(name)
                        onBackClick()
                    }
                }
            }.invokeOnCompletion {
                editResult.value = editResult.value.onCoroutineDeath()
            }
        }
    }

}