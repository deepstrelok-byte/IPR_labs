package components

import architecture.DefaultStack
import auth.components.AuthComponent
import components.RootComponent.Child
import components.RootComponent.Config
import itemEditorFlow.components.ItemEditorFlowComponent
import kotlinx.serialization.Serializable
import logic.ItemManagerPreData
import logic.QuickProfileData
import mainFlow.components.MainFlowComponent
import profileFlow.components.ProfileFlowComponent
import registration.components.RegistrationComponent

interface RootComponent : DefaultStack<Config, Child> {

    sealed interface Child {
        data class HelloChild(val helloComponent: HelloComponent) : Child

        data class AuthChild(val authComponent: AuthComponent) : Child

        data class RegistrationChild(val registrationComponent: RegistrationComponent) : Child

        data class MainFlowChild(val mainFlowComponent: MainFlowComponent) : Child

        data class ItemEditorChild(val itemEditorComponent: ItemEditorFlowComponent) : Child
        data class ProfileFlowChild(val profileFlowComponent: ProfileFlowComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Hello : Config

        @Serializable
        data object Auth : Config

        @Serializable
        data object Registration : Config

        @Serializable
        data object MainFlow : Config

        @Serializable
        data class ItemEditor(
            val itemManagerPreData: ItemManagerPreData
        ) : Config


        @Serializable
        data class ProfileFlow(val userData: Pair<String, QuickProfileData>?, val openVerification: Boolean) : Config
    }
}