package components.outputHandlers

import auth.components.AuthComponent
import com.arkivanov.decompose.router.stack.replaceAll
import components.RootComponent
import components.RootComponent.Config

fun RootComponent.onAuthOutput(
    output: AuthComponent.Output
) {
    when (output) {
        AuthComponent.Output.NavigateToMain -> nav.replaceAll(Config.MainFlow)
        AuthComponent.Output.NavigateToRegistration -> nav.replaceAll(Config.Registration)
    }
}