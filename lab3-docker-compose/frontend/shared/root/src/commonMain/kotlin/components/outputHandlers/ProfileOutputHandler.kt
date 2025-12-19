package components.outputHandlers

import com.arkivanov.decompose.router.stack.replaceAll
import components.RootComponent
import components.RootComponent.Config
import profileFlow.components.ProfileFlowComponent

fun RootComponent.onProfileFlowOutput(
    output: ProfileFlowComponent.Output
) {
    when (output) {
        ProfileFlowComponent.Output.NavigateToAuth -> nav.replaceAll(Config.Auth)
        ProfileFlowComponent.Output.Back ->
            popOnce(RootComponent.Child.ProfileFlowChild::class)

    }
}