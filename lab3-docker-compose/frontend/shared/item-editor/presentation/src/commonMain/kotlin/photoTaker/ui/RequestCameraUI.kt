package photoTaker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import camera.CameraPermissionManager

@Composable
internal fun RequestCameraUI(
    cameraPermissionManager: CameraPermissionManager
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cameraPermissionManager.isDenied()) {
            Text("Разрешите в настройках!")
        }
        Button(onClick = { cameraPermissionManager.requestCameraPermission() }) {
            Text("Разрешить камеру")
        }
    }
}