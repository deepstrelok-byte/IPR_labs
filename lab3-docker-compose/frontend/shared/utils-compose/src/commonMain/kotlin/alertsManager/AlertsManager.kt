package alertsManager

import architecture.launchIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.EmptyCoroutineContext

object AlertsManager {
    private val _alertState: MutableStateFlow<AlertState> = MutableStateFlow(AlertState.Hidden)
    val alertState = _alertState.asStateFlow()

    private var job: Job? = null

    fun push(
        alert: AlertState,
        duration: Long = if (alert is AlertState.SuccessDialog) 1000L else 3000L
    ) {
        job?.cancel()

        _alertState.value = alert

        job = CoroutineScope(EmptyCoroutineContext).launchIO {
            delay(duration)
        }.also {
            it.invokeOnCompletion {
                hideNotification()
            }
        }
    }

    fun hideNotification() {
        _alertState.value = AlertState.Hidden
        job?.cancel()
    }
}