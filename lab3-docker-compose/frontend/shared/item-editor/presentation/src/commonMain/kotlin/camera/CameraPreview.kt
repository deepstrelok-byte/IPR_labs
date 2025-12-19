package camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun CameraPreview(modifier: Modifier = Modifier.fillMaxSize(), cameraCallback: CameraCallback)
