package camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType

actual class CameraPermissionManager actual constructor() {

    val status: MutableState<Long> = mutableStateOf(AVAuthorizationStatusNotDetermined)

    @Composable
    actual fun isCameraAllowed(): Boolean {
        status.value = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
        return status.value == AVAuthorizationStatusAuthorized
    }

    actual fun requestCameraPermission() {
        AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) {
            status.value = if (it) AVAuthorizationStatusAuthorized else AVAuthorizationStatusDenied
        }
    }

    actual fun isDenied(): Boolean {
        return status.value == AVAuthorizationStatusDenied
    }
}