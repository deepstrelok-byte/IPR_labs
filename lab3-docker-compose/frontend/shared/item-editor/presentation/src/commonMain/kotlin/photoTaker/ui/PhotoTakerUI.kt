package photoTaker.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import camera.CameraPermissionManager
import photoTaker.components.PhotoTakerComponent

@Composable
fun PhotoTakerUI(
    component: PhotoTakerComponent,
) {
    val cameraPermissionManager = remember { CameraPermissionManager() }

    if (cameraPermissionManager.isCameraAllowed()) {
        CameraUI(component = component )
    } else {
        RequestCameraUI(cameraPermissionManager)
    }
}