package profileFlow.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import logic.QuickProfileData
import myProfile.components.RealMyProfileComponent
import org.koin.core.component.KoinComponent
import profileFlow.components.ProfileFlowComponent.Child
import profileFlow.components.ProfileFlowComponent.Child.MyProfileChild
import profileFlow.components.ProfileFlowComponent.Config
import profileFlow.components.ProfileFlowComponent.Output
import transactions.components.RealTransactionsComponent

class RealProfileFlowComponent(
    componentContext: ComponentContext,
    private val userData: Pair<String, QuickProfileData>?,
    private val openVerification: Boolean = false,
    private val onVerifiedChange: (Boolean) -> Unit,
    val output: (Output) -> Unit
) : ProfileFlowComponent, KoinComponent, ComponentContext by componentContext {


    override val nav = StackNavigation<Config>()


    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialConfiguration = calculateInitialConfig(),
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack


    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.MyProfile -> MyProfileChild(
                RealMyProfileComponent(
                    childContext,
                    openVerification = openVerification,
                    goToAuth = { output(Output.NavigateToAuth) },
                    goToMain = { output(Output.Back) },
                    goToTransactions = { profileData, userId ->
                        nav.bringToFront(
                            Config.Transactions(profileData, userId)
                        )
                    },
                    onVerifiedChange = onVerifiedChange
                )
            )

            is Config.Transactions -> Child.TransactionsChild(
                RealTransactionsComponent(
                    childContext,
                    profileData = config.profileData,
                    userId = config.userId,
                    pop = {
                        if (calculateInitialConfig() !is Config.Transactions) {
                            popOnce(Child.TransactionsChild::class)
                        } else {
                            output.invoke(Output.Back)
                        }
                    }
                )
            )
        }
    }


    private fun calculateInitialConfig(): Config {
        return if (userData == null) Config.MyProfile else Config.Transactions(
            profileData = userData.second,
            userId = userData.first
        )
    }
}