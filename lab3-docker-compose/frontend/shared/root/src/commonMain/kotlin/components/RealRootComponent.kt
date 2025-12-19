package components

import FontSizeManager
import auth.components.RealAuthComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.items
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import components.RootComponent.Child
import components.RootComponent.Child.AuthChild
import components.RootComponent.Child.HelloChild
import components.RootComponent.Child.ItemEditorChild
import components.RootComponent.Child.MainFlowChild
import components.RootComponent.Child.ProfileFlowChild
import components.RootComponent.Child.RegistrationChild
import components.RootComponent.Config
import components.outputHandlers.onAuthOutput
import components.outputHandlers.onHelloOutput
import components.outputHandlers.onMainOutput
import components.outputHandlers.onProfileFlowOutput
import itemEditorFlow.components.RealItemEditorFlowComponent
import mainFlow.components.MainFlowComponent
import mainFlow.components.RealMainFlowComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import profileFlow.components.RealProfileFlowComponent
import registration.components.RealRegistrationComponent
import usecases.AuthUseCases
import usecases.SettingsUseCases
import usecases.UserUseCases

class RealRootComponent(
    componentContext: ComponentContext
) : RootComponent, KoinComponent, ComponentContext by componentContext {


    init {
        val settingsUseCases: SettingsUseCases = get()
        FontSizeManager.setFontSize(settingsUseCases.fetchFontSize())
    }

    override val nav = StackNavigation<Config>()
    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialConfiguration = getInitialConfig(),
            childFactory = ::child,
            handleBackButton = true
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack

    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.Hello -> HelloChild(
                RealHelloComponent(
                    childContext,
                    output = ::onHelloOutput
                )
            )

            Config.MainFlow -> MainFlowChild(
                RealMainFlowComponent(childContext, output = ::onMainOutput)
            )

            is Config.ItemEditor -> ItemEditorChild(
                RealItemEditorFlowComponent(
                    childContext,
                    exitFromFlow = { popOnce(ItemEditorChild::class) },
                    itemManagerPreData = config.itemManagerPreData,
                    fetchShareCareItems = {
                        val mainFlowComponent =
                            (stack.items.firstOrNull { it.configuration is Config.MainFlow }?.instance as? Child.MainFlowChild)?.mainFlowComponent
                        mainFlowComponent?.let { main ->
                            val findHelpComponent =
                                (main.stack.items.firstOrNull { it.configuration is MainFlowComponent.Config.FindHelp }?.instance as? MainFlowComponent.Child.FindHelpChild)?.findHelpComponent
                            val shareCareComponent =
                                (main.stack.items.firstOrNull { it.configuration is MainFlowComponent.Config.ShareCare }?.instance as? MainFlowComponent.Child.ShareCareChild)?.shareCareComponent

                            findHelpComponent?.let {
                                val data = findHelpComponent.items.value.data
                                if (data != null && config.itemManagerPreData.itemId in data.map { it.id }) {
                                    findHelpComponent.onSearch(resetItems = true)
                                }
                            }
                            shareCareComponent?.fetchItems()

                        }
                    }
                )
            )

            Config.Auth -> AuthChild(
                RealAuthComponent(
                    childContext,
                    output = ::onAuthOutput
                )
            )

            Config.Registration -> RegistrationChild(
                RealRegistrationComponent(childContext) {
                    nav.replaceAll(Config.MainFlow)
                }
            )

            is Config.ProfileFlow -> ProfileFlowChild(
                RealProfileFlowComponent(
                    childContext,
                    userData = config.userData,
                    openVerification = config.openVerification,
                    onVerifiedChange = { isVerified ->
                        val mainFlowComponent =
                            (stack.items.firstOrNull { it.configuration is Config.MainFlow }?.instance as? Child.MainFlowChild)?.mainFlowComponent
                        mainFlowComponent?.isVerified?.value = isVerified
                    },
                    output = ::onProfileFlowOutput
                )
            )
        }
    }

    private fun getInitialConfig(): Config {
        val authUseCases: AuthUseCases = get()
        val userUseCases: UserUseCases = get()

        val hasToken = authUseCases.fetchToken() != null
        val hasName = userUseCases.fetchName() != null

        return if (hasToken) {
            if (hasName) Config.MainFlow
            else Config.Registration
        } else Config.Hello
    }
}