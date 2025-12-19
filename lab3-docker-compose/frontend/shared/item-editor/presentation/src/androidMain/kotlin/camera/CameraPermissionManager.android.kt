package camera

import android.Manifest
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

actual class CameraPermissionManager {

    @OptIn(ExperimentalPermissionsApi::class)
    lateinit var cameraPermissionState: PermissionState

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    actual fun isCameraAllowed(): Boolean {
        cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
        return cameraPermissionState.status.isGranted
    }

    @OptIn(ExperimentalPermissionsApi::class)
    actual fun requestCameraPermission() {
        cameraPermissionState.launchPermissionRequest()
    }

    @OptIn(ExperimentalPermissionsApi::class)
    actual fun isDenied(): Boolean {
        return cameraPermissionState.status.shouldShowRationale
    }

}

// mb: Remove Composable
// private val activityResultLauncher =
//    registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions())
//    { permissions ->
//        // Handle Permission granted/rejected
//        var permissionGranted = true
//        permissions.entries.forEach {
//            if (it.key in REQUIRED_PERMISSIONS && it.value == false)
//                permissionGranted = false
//        }
//        if (!permissionGranted) {
//            Toast.makeText(baseContext,
//                "Permission request denied",
//                Toast.LENGTH_SHORT).show()
//        } else {
//            startCamera()
//        }
//    }