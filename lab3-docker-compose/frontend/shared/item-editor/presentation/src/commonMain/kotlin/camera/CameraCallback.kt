package camera


import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow


sealed interface CameraEvent {
    object CaptureImage : CameraEvent
//    object SwitchFlash : CameraEvent
}

class CameraCallback(
    val onCaptureImage: (image: ImageBitmap) -> Unit
) {
    private val _event = Channel<CameraEvent>()
    val eventFlow: Flow<CameraEvent> get() = _event.receiveAsFlow()
    suspend fun sendEvent(event: CameraEvent) {
        this._event.send(event)
    }
}