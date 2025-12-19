package mainFlow.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.items
import com.arkivanov.decompose.value.Value
import common.detailsInterfaces.DetailsComponent
import common.detailsInterfaces.DetailsConfig
import common.detailsInterfaces.DetailsConfig.ItemDetailsConfig
import decompose.componentCoroutineScope
import enums.UsuallyI
import findHelp.components.RealFindHelpComponent
import itemDetails.components.RealItemDetailsComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import loading.components.LoadingComponent
import loading.components.RealLoadingComponent
import logic.ItemManagerPreData
import logic.enums.ItemStatus
import mainFlow.components.MainFlowComponent.Child
import mainFlow.components.MainFlowComponent.Child.FindHelpChild
import mainFlow.components.MainFlowComponent.Child.ShareCareChild
import mainFlow.components.MainFlowComponent.Config
import mainFlow.components.MainFlowComponent.Output
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import requestDetails.components.RealRequestDetailsComponent
import shareCare.components.RealShareCareComponent
import usecases.AuthUseCases
import usecases.SettingsUseCases
import usecases.UserUseCases

class RealMainFlowComponent(
    componentContext: ComponentContext,
    override val output: (Output) -> Unit
) : MainFlowComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    private val authUseCases: AuthUseCases = get()
    private val userUseCases: UserUseCases = get()
    private val settingsUseCases: SettingsUseCases = get()

    private val usuallyI = settingsUseCases.fetchUsuallyI()
    override val isVerified: MutableStateFlow<Boolean> =
        MutableStateFlow(userUseCases.fetchIsVerified())

    override val loadingComponent: LoadingComponent =
        RealLoadingComponent(
            componentContext.childContext("loadingComponent"),
            navigateToAuth = { output(Output.NavigateToAuth) },
            navigateToRegistration = { output(Output.NavigateToRegistration) }
        )

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

    override val detailsNav = SlotNavigation<DetailsConfig>()
    private val _detailsSlot: Value<ChildSlot<*, DetailsComponent>> =
        childSlot(
            source = detailsNav,
            serializer = DetailsConfig.serializer(),
            handleBackButton = true,
            childFactory = { cfg, ctx ->
                val currentId = authUseCases.fetchUserId().toString()
                when (cfg) {
                    is ItemDetailsConfig ->
                        RealItemDetailsComponent(
                            ctx,
                            id = cfg.id,
                            key = cfg.key,
                            images = cfg.images,
                            currentId = currentId,
                            creatorId = cfg.creatorId,
                            title = cfg.title,
                            description = cfg.description,
                            location = cfg.location,
                            category = cfg.category,
                            deliveryTypes = cfg.deliveryTypes,
                            recipientId = cfg.recipientId,
                            telegram = cfg.telegram,
                            takeItemFromFindHelp = { telegram ->
                                (stack.items.firstOrNull { it.configuration is Config.FindHelp }?.instance as? Child.FindHelpChild)?.findHelpComponent?.takeItem(
                                    cfg.id,
                                    telegram = telegram
                                )
                            },
                            denyItemFromFlow = {
                                when (val child = (stack.items.last()).instance) {
                                    is FindHelpChild -> child.findHelpComponent.denyItem(cfg.id)
                                    is ShareCareChild -> child.shareCareComponent.denyItem(cfg.id)
                                }
                            },
                            deleteItemFromFlow = { closeSheet, deleteFromReadyToHelp ->
                                val findHelpComponent =
                                    (stack.items.firstOrNull { it.configuration is Config.FindHelp }?.instance as? Child.FindHelpChild)?.findHelpComponent
                                val shareCareComponent =
                                    (stack.items.firstOrNull { it.configuration is Config.ShareCare }?.instance as? Child.ShareCareChild)?.shareCareComponent

                                findHelpComponent?.let {
                                    val data = findHelpComponent.items.value.data
                                    if (data != null && cfg.id in data.map { it.id }) {
                                        findHelpComponent.onSearch(resetItems = true)
                                    }
                                }

                                closeSheet {
                                    // onCompletion
                                    shareCareComponent?.fetchItems()

                                    if (deleteFromReadyToHelp) {
                                        findHelpComponent?.fetchBasic()
                                    }
                                }
                            },
                            onEditClick = {
                                output(
                                    Output.NavigateToItemEditor(
                                        itemManagerPreData = ItemManagerPreData(
                                            title = cfg.title,
                                            description = cfg.description,
                                            deliveryTypes = cfg.deliveryTypes,
                                            category = cfg.category,
                                            location = cfg.location,
                                            images = cfg.images,
                                            itemId = cfg.id
                                        )
                                    )
                                )
                            },
                            goToTransactions = { profileData, userId ->
                                output(
                                    Output.NavigateToProfile(
                                        profileData = profileData,
                                        userId = userId,
                                        openVerification = false
                                    )
                                )
                            }
                        )

                    is DetailsConfig.RequestDetailsConfig ->
                        RealRequestDetailsComponent(
                            ctx,
                            id = cfg.id,
                            key = cfg.key,
                            currentId = currentId,
                            creatorId = cfg.creatorId,
                            initialText = cfg.text,
                            initialCategory = cfg.category,
                            initialDeliveryTypes = cfg.deliveryTypes,
                            updateFindHelpFlow = {
                                (stack.items.firstOrNull { it.configuration is Config.FindHelp }?.instance as? Child.FindHelpChild)?.findHelpComponent?.fetchBasic()
                            },
                            onBackClick = { detailsNav.dismiss() },
                            onAcceptClick = { itemQuickInfo ->
                                if (itemQuickInfo.data == null) {
                                    AlertsManager.push(AlertState.SnackBar(message = "Пожалуйста, подождите загрузки данных"))
                                } else if (itemQuickInfo.data?.status != ItemStatus.Listed) {
                                    AlertsManager.push(AlertState.SnackBar(message = "Кто-то другой уже откликнулся, спасибо за отзывчивость!"))
                                } else {
                                    detailsNav.dismiss()
                                    output(
                                        Output.NavigateToItemEditor(
                                            itemManagerPreData = ItemManagerPreData(
                                                title = cfg.text,
                                                category = cfg.category,
                                                availableDeliveryTypes = cfg.deliveryTypes,
                                                location = "Москва, м. Сокол",
                                                requestId = cfg.id
                                            )
                                        )
                                    )
                                }
                            },
                            goToTransactions = { profileData, userId ->
                                output(
                                    Output.NavigateToProfile(
                                        profileData = profileData,
                                        userId = userId,
                                        openVerification = false
                                    )
                                )
                            }
                        )
                }
            }

        )


    override val detailsSlot: Value<ChildSlot<*, DetailsComponent>>
        get() = _detailsSlot


    private fun openDetails(cfg: DetailsConfig) {
        detailsNav.activate(cfg)
    }

    private fun child(config: Config, childContext: ComponentContext): Child {
        return when (config) {
            Config.FindHelp -> FindHelpChild(
                RealFindHelpComponent(
                    childContext, openDetails = ::openDetails
                )
            )

            Config.ShareCare -> ShareCareChild(
                RealShareCareComponent(childContext, openDetails = ::openDetails)
            )
        }
    }

    override fun onBackClicked() {
        val activeCfg = _stack.active.configuration

        // TODO
        when {
            (usuallyI == UsuallyI.FindHelp && activeCfg is Config.FindHelp) -> {
                nav.bringToFront(Config.ShareCare)
            }
            (usuallyI == UsuallyI.ShareCare && activeCfg is Config.ShareCare) -> {
                nav.bringToFront(Config.FindHelp)
            }
            else -> {
                super.onBackClicked()
            }
        }
    }


    private fun calculateInitialConfig(): Config {
        return if (usuallyI == UsuallyI.FindHelp) Config.FindHelp else Config.ShareCare
    }


    override fun navigateTo(cfg: Config) {
        nav.bringToFront(cfg)
    }

    init {
        coroutineScope.launchIO {
            delay(500) //0_oo
            isVerified.value = userUseCases.fetchIsVerified()
        }
    }
}