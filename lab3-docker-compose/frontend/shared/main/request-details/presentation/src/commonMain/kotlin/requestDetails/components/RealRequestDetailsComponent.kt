package requestDetails.components

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.foundation.text.input.TextFieldState
import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import entities.Request
import entity.ItemQuickInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import logic.QuickProfileData
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.RequestDetailsUseCases

class RealRequestDetailsComponent(
    componentContext: ComponentContext,
    override val id: String,
    override val key: String,
    override val creatorId: String,
    override val currentId: String,

    override val location: String = "Москва, м. Сокол",

    override val initialText: String,
    override val initialCategory: ItemCategory?,
    override val initialDeliveryTypes: List<DeliveryType>,


    override val onBackClick: () -> Unit,
    override val onAcceptClick: (NetworkState<ItemQuickInfo>) -> Unit,

    private val updateFindHelpFlow: () -> Unit,
    private val goToTransactions: (QuickProfileData, String) -> Unit
) : RequestDetailsComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val requestDetailsUseCases: RequestDetailsUseCases = get()

    override val isEditable: Boolean =
        id == "Create" || currentId == creatorId // meow
    override val isCreating: Boolean =
        isEditable && creatorId == ""

    override val requestText: TextFieldState = TextFieldState(initialText)
    override val category: MutableStateFlow<ItemCategory?> = MutableStateFlow(initialCategory)
    override val deliveryTypes: MutableStateFlow<List<DeliveryType>> =
        MutableStateFlow(initialDeliveryTypes)
    override val createOrEditRequestResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)
    override val deleteRequestResult: MutableStateFlow<NetworkState<Unit>> =
        MutableStateFlow(NetworkState.AFK)


    // sorry for boilerplate =( (from ItemEditor)
    override fun updateCategory(category: ItemCategory) {
        if (!createOrEditRequestResult.value.isLoading()) {
            this.category.value = category
        }
    }

    override fun deleteRequest() {
        require(isEditable)
        if (!deleteRequestResult.value.isLoading()) {
            coroutineScope.launchIO {
                requestDetailsUseCases.deleteRequest(id).collect {
                    deleteRequestResult.value = it
                }
                withContext(Dispatchers.Main) {
                    deleteRequestResult.value.handle(
                        onError = { AlertsManager.push(AlertState.SnackBar(it.prettyPrint)) }
                    ) {
                        onBackClick()
                        updateFindHelpFlow()
                    }
                }

            }.invokeOnCompletion {
                deleteRequestResult.value = deleteRequestResult.value.onCoroutineDeath()
            }
        }
    }

    override fun createOrEditRequest() {
        require(isEditable)
        if (!createOrEditRequestResult.value.isLoading()) {
            coroutineScope.launchIO {
                val preparedRequest = Request(
                    text = requestText.text.toString(),
                    category = category.value!!,
                    deliveryTypes = deliveryTypes.value,
                    location = "Москва, м. Сокол" // TODO
                )
                (if (isCreating) requestDetailsUseCases.createRequest(preparedRequest) else requestDetailsUseCases.editRequest(
                    preparedRequest,
                    id
                )).collect {
                    createOrEditRequestResult.value = it
                }
                withContext(Dispatchers.Main) {
                    createOrEditRequestResult.value.handle(
                        onError = { AlertsManager.push(AlertState.SnackBar(it.prettyPrint)) }
                    ) {
                        if (isCreating) {
                            AlertsManager.push(
                                AlertState.SuccessDialog("Заявка создана")
                            )
                        }
                        updateFindHelpFlow()
                        onBackClick()
                    }
                }

            }.invokeOnCompletion {
                createOrEditRequestResult.value = createOrEditRequestResult.value.onCoroutineDeath()
            }
        }
    }


    override fun updateDeliveryType(deliveryType: DeliveryType) {
        if (!createOrEditRequestResult.value.isLoading() && isEditable) {
            if (deliveryType in this.deliveryTypes.value) {
                removeDeliveryType(deliveryType)
            } else {
                addDeliveryType(deliveryType)
            }
        }
    }

    private fun addDeliveryType(deliveryType: DeliveryType) {
        this.deliveryTypes.update { current -> current + deliveryType }
    }

    private fun removeDeliveryType(deliveryType: DeliveryType) {
        this.deliveryTypes.update { current -> current - deliveryType }
    }


    override val requestQuickInfo: MutableStateFlow<NetworkState<ItemQuickInfo>> =
        MutableStateFlow(NetworkState.AFK)

    override fun fetchRequestQuickInfo() {
        if (!requestQuickInfo.value.isLoading()) {
            coroutineScope.launchIO {
                requestDetailsUseCases.fetchRequestQuickInfo(id).collect {
                    requestQuickInfo.value = it
                }
            }.invokeOnCompletion {
                requestQuickInfo.value = requestQuickInfo.value.onCoroutineDeath()
            }
        }
    }

    override fun onProfileClick() {
        val data = requestQuickInfo.value.data
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
        if (!isEditable) {
            fetchRequestQuickInfo()
        }
    }
}