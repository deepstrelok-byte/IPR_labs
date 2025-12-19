package camera

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Rational
import android.view.Surface
import androidx.annotation.OptIn
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.viewfinder.core.ImplementationMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.awaitCancellation

@SuppressLint("RestrictedApi")
@Composable
actual fun CameraPreview(modifier: Modifier, cameraCallback: CameraCallback) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var surfaceRequest: SurfaceRequest? by remember { mutableStateOf(null) }


    val imageCapture: ImageCapture =
        remember { ImageCapture.Builder().build() }

    val cameraPreviewUseCase = remember {
        Preview.Builder().setTargetRotation(Surface.ROTATION_0).build().apply {
            setSurfaceProvider { newSurfaceRequest ->
                surfaceRequest = newSurfaceRequest
            }
        }
    }

    fun takePicture() {
        val resolution = cameraPreviewUseCase.attachedSurfaceResolution


        if (resolution != null) {
            val aspectRatio = Rational(resolution.width, resolution.height)
            imageCapture.setCropAspectRatio(aspectRatio)
        }


        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                @OptIn(ExperimentalGetImage::class)
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    val bitmap = imageProxy.toBitmap()
                    cameraCallback.onCaptureImage(bitmap.asImageBitmap())
                    imageProxy.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    // Handle exception
                }
            }
        )

    }


    LaunchedEffect(Unit) {
        cameraCallback.eventFlow.collect {
            when (it) {
                CameraEvent.CaptureImage -> takePicture()
            }
        }
    }


    LaunchedEffect(lifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(context)
        processCameraProvider.bindToLifecycle(
            lifecycleOwner, DEFAULT_BACK_CAMERA, cameraPreviewUseCase, imageCapture
        )
        // Cancellation signals we're done with the camera
        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }

    }

    surfaceRequest?.let { sr ->
        CameraXViewfinder(
            surfaceRequest = sr,
            modifier = modifier,
            implementationMode = ImplementationMode.EMBEDDED
        )
    }

}

// wtf.
// ImageProxy.toBitmap() не учитывает поворот imageCapture....
//private fun Bitmap.rotated(degrees: Int): Bitmap {
//    val source = this
//    val matrix = Matrix()
//    matrix.postRotate(degrees.toFloat())
//    return Bitmap.createBitmap(
//        source, 0, 0, source.width, source.height, matrix, true
//    )
//}