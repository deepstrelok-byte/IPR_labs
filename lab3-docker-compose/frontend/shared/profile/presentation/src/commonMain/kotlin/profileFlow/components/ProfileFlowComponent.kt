package profileFlow.components

import architecture.DefaultStack
import kotlinx.serialization.Serializable
import logic.QuickProfileData
import myProfile.components.MyProfileComponent
import profileFlow.components.ProfileFlowComponent.Child
import profileFlow.components.ProfileFlowComponent.Config
import transactions.components.TransactionsComponent

interface ProfileFlowComponent : DefaultStack<Config, Child> {

    sealed interface Child {
        data class MyProfileChild(val myProfileComponent: MyProfileComponent) :
            Child

        data class TransactionsChild(val transactionsComponent: TransactionsComponent) :
            Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object MyProfile : Config

        @Serializable
        data class Transactions(val profileData: QuickProfileData, val userId: String) : Config
    }


    sealed class Output {
        data object NavigateToAuth : Output()
        data object Back : Output()
    }


}