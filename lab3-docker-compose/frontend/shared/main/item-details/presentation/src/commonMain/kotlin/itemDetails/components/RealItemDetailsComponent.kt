package itemDetails.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import entities.TakeItemResponse
import entity.ItemQuickInfo
import itemDetails.TelegramOpener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import logic.QuickProfileData
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import logic.enums.ItemStatus
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.ItemDetailsUseCases
import usecases.UserUseCases

class RealItemDetailsComponent(
    componentContext: ComponentContext,
    override val id: String,
    override val currentId: String,
    override val creatorId: String,
    override val images: List<String>,
    override val title: String,
    override val description: String,
    override val location: String,
    override val category: ItemCategory,
    override val deliveryTypes: List<DeliveryType>,
    override val key: String,
    recipientId: String?,
    telegram: String?,
    private val takeItemFromFindHelp: (String) -> Unit,
    private val denyItemFromFlow: () -> Unit,
    private val deleteItemFromFlow: ((() -> Unit) -> Unit, Boolean) -> Unit,
    override val onEditClick: () -> Unit,
    private val goToTransactions: (QuickProfileData, String) -> Unit
) : ItemDetailsComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val itemDetailsUseCases: ItemDetailsUseCases = get()
    private val userUseCases: UserUseCases = get()

    private val telegramOpener: TelegramOpener = get()

    override val isOwner = currentId == creatorId
    override val telegram: MutableStateFlow<String?> = MutableStateFlow(telegram)

    override val recipientId: MutableStateFlow<String?> = MutableStateFlow(recipientId)


    override val takeItemResult: MutableStateFlow<NetworkState<TakeItemResponse>> =
        MutableStateFlow(NetworkState.AFK)
    override val operationItemResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)

    override fun takeItem() {
        if (!userUseCases.fetchIsVerified()) {
            AlertsManager.push(AlertState.SnackBar(message = "Сначала необходимо верифицировать свой аккаунт"))
        } else if (itemQuickInfo.value.data == null) {
            AlertsManager.push(AlertState.SnackBar(message = "Пожалуйста, подождите загрузки данных"))
        } else if (itemQuickInfo.value.data?.status != ItemStatus.Listed) {
            AlertsManager.push(AlertState.SnackBar(message = "Предмет уже забрали"))
        } else if (recipientId.value == null && !isOwner && !takeItemResult.value.isLoading()) {
            coroutineScope.launchIO {
                itemDetailsUseCases.takeItem(id).collect {
                    takeItemResult.value = it
                }
                takeItemResult.value.handle(
                    onError = {
                        AlertsManager.push(AlertState.SnackBar(message = it.prettyPrint))
                    }
                ) {
                    val telegramUsername = it.data.telegram
                    telegram.value = telegramUsername
                    recipientId.value = currentId
                    takeItemFromFindHelp(telegramUsername)
                }
            }.invokeOnCompletion { takeItemResult.value = takeItemResult.value.onCoroutineDeath() }
        }
    }

    override fun acceptItem(closeSheet: (() -> Unit) -> Unit) {
        if (recipientId.value != null && !operationItemResult.value.isLoading()) {
            defaultOperationRequest(
                onSuccess = {
                    deleteItemFromFlow(closeSheet, true)
                },
                useCase = { itemDetailsUseCases.acceptItem(id) }
            )
        }
    }

    override fun denyItem() {
        if (recipientId.value != null && !operationItemResult.value.isLoading()) {
            defaultOperationRequest(
                onSuccess = {
                    recipientId.value = null
                    denyItemFromFlow()
                },
                useCase = { itemDetailsUseCases.denyItem(id) }
            )
        }
    }

    override fun deleteItem(closeSheet: (() -> Unit) -> Unit) {
        if (!operationItemResult.value.isLoading()) {
            defaultOperationRequest(
                onSuccess = {
                    deleteItemFromFlow(closeSheet, false)
                },
                useCase = { itemDetailsUseCases.deleteItem(id) }
            )
        }
    }

    override val itemQuickInfo: MutableStateFlow<NetworkState<ItemQuickInfo>> =
        MutableStateFlow(NetworkState.AFK)

    override fun fetchItemQuickInfo() {
        if (!itemQuickInfo.value.isLoading()) {
            coroutineScope.launchIO {
                itemDetailsUseCases.fetchItemQuickInfo(id).collect {
                    itemQuickInfo.value = it
                }
            }.invokeOnCompletion {
                itemQuickInfo.value = itemQuickInfo.value.onCoroutineDeath()
            }
        }
    }

    override fun openTelegram() {
        if (telegram.value == null) {
            AlertsManager.push(
                AlertState.SnackBar(
                    "Неизвестный телеграм"
                )
            )
        } else {
            telegramOpener.open(telegram.value!!)
        }
    }

    override fun onProfileClick() {
        val data = itemQuickInfo.value.data
        data?.let {
            goToTransactions(
                QuickProfileData(
                    name = data.opponentName,
                    isVerified = data.opponentIsVerified,
                    organizationName = data.opponentOrganizationName
                ),
                data.opponentId
            )
        }
    }

    init {
        fetchItemQuickInfo()
    }

    private fun defaultOperationRequest(
        onSuccess: () -> Unit,
        useCase: () -> Flow<NetworkState<Unit>>
    ) {
        coroutineScope.launchIO {
            useCase().collect {
                operationItemResult.value = it
            }
            operationItemResult.value.handle(
                onError = {
                    AlertsManager.push(AlertState.SnackBar(message = it.prettyPrint))
                }
            ) {
                onSuccess()
            }
        }
            .invokeOnCompletion {
                operationItemResult.value = operationItemResult.value.onCoroutineDeath()
            }
    }
}