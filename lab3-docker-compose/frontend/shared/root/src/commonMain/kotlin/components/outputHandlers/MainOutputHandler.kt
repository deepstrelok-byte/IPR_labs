package components.outputHandlers

import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.replaceAll
import components.RootComponent
import components.RootComponent.Config
import mainFlow.components.MainFlowComponent

fun RootComponent.onMainOutput(
    output: MainFlowComponent.Output
) {
    when (output) {
        is MainFlowComponent.Output.NavigateToItemEditor -> nav.bringToFront(
            Config.ItemEditor(
                itemManagerPreData = output.itemManagerPreData

            )
        )

        MainFlowComponent.Output.NavigateToAuth -> nav.replaceAll(Config.Auth)
        MainFlowComponent.Output.NavigateToRegistration -> nav.replaceAll(Config.Registration)
        is MainFlowComponent.Output.NavigateToProfile -> nav.bringToFront(
            Config.ProfileFlow(
                userData = if (output.profileData != null && output.userId != null) output.userId!! to output.profileData!! else null,
                openVerification = output.openVerification
            )
        )
    }
}