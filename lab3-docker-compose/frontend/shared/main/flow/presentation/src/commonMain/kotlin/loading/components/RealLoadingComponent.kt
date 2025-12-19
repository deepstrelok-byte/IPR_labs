package loading.components

import architecture.launchIO
import com.arkivanov.decompose.ComponentContext
import decompose.componentCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import network.NetworkState
import network.NetworkState.AFK.onCoroutineDeath
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import usecases.UserUseCases
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class RealLoadingComponent(
    componentContext: ComponentContext,
    private val navigateToRegistration: () -> Unit,
    private val navigateToAuth: () -> Unit
) : LoadingComponent, KoinComponent, ComponentContext by componentContext {
    private val userUseCases: UserUseCases = get()

    private val coroutineScope = componentCoroutineScope()


    override val updateUserInfoResult =
        MutableStateFlow<NetworkState<Unit>>(NetworkState.AFK)


    init {
        updateUserInfo()

    }

    override fun updateUserInfo() {
        if (!updateUserInfoResult.value.isLoading()) {
            coroutineScope.launchIO {
                // TODO: remove
                delay(400)
                userUseCases.updateCurrentUserInfo().collect {
                    updateUserInfoResult.value = it
                }
                withContext(Dispatchers.Main) {
                    updateUserInfoResult.value.onError { result ->
                        if (result.code == 400) { // Не зареган (с токеном всё ок)
                            navigateToRegistration()
                        } else if (result.code == 401 || result.code == 403) { // Токен умер
                            navigateToAuth()
                        }
                    }
                }
            }.invokeOnCompletion {
                updateUserInfoResult.value = updateUserInfoResult.value.onCoroutineDeath()
            }
        }
    }

    override val helloText: String
        get() = getNamedHelloText()

    @OptIn(ExperimentalTime::class)
    fun getNamedHelloText(): String {
        val name = userUseCases.fetchName()
        val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
        val dayTime = when (currentHour) {
            in 5..10 -> "Доброе утро"
            in 11..18 -> "Добрый день"
            in 19..21 -> "Добрый вечер"
            else -> "Доброй ночи"
        }

        return name?.let {
            "$dayTime,\n$name!"
        } ?: "$dayTime!"
    }

}