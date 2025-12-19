package transactions.components

import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import entities.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import logic.QuickProfileData
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import transactions.components.TransactionsComponent.TransactionsInfo
import usecases.TransactionsUseCases

class RealTransactionsComponent(
    componentContext: ComponentContext,
    override val profileData: QuickProfileData,
    override val userId: String,
    override val pop: () -> Unit,
) : TransactionsComponent, KoinComponent, ComponentContext by componentContext {


    private val coroutineScope = componentCoroutineScope()
    private val transactionsUseCases: TransactionsUseCases = get()

    override val transactionsInfo: MutableStateFlow<TransactionsInfo?> =
        MutableStateFlow(null)
    override val transactions: MutableStateFlow<NetworkState<List<Transaction>>> =
        MutableStateFlow(NetworkState.AFK)

    init {
        fetchTransactions()
    }

    override fun fetchTransactions() {
        if (!transactions.value.isLoading()) {
            coroutineScope.launchIO {
                transactionsUseCases.fetchTransactions(userId).collect {
                    transactions.value = it
                }
                val data = transactions.value.data
                data?.let {
                    var received = 0
                    var donated = 0
                    data.forEach {
                        if (it.isRecipient) received++
                        else donated++
                    }
                    transactionsInfo.value =
                        TransactionsInfo(received = received, donated = donated)
                }
            }.invokeOnCompletion {
                transactions.value = transactions.value.onCoroutineDeath()
            }
        }
    }

}