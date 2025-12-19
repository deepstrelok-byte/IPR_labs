package camera

import androidx.compose.runtime.Composable

expect class CameraPermissionManager() {
    @Composable
    fun isCameraAllowed(): Boolean

    fun requestCameraPermission()

    fun isDenied(): Boolean
}
