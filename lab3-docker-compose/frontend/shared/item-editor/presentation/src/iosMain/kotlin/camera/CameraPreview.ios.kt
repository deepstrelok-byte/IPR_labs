package camera

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDeviceInput.Companion.deviceInputWithDevice
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.position
import platform.CoreGraphics.CGRectZero
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraPreview(
    modifier: Modifier,
    cameraCallback: CameraCallback
) {
    val camera = AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull { device ->
        (device as AVCaptureDevice).position == AVCaptureDevicePositionBack
    } as? AVCaptureDevice


    if (camera != null) {
        val captureSession: AVCaptureSession = remember {
            AVCaptureSession().also { captureSession ->
                captureSession.sessionPreset = AVCaptureSessionPresetPhoto
                val captureDeviceInput: AVCaptureDeviceInput =
                    deviceInputWithDevice(device = camera, error = null)!!
                captureSession.addInput(captureDeviceInput)
            }
        }

        val cameraPreviewLayer = remember {
            AVCaptureVideoPreviewLayer(session = captureSession)
        }

        UIKitView(
            modifier = modifier,
            factory = {
                val cameraContainer = object : UIView(frame = CGRectZero.readValue()) {
                    override fun layoutSubviews() {
                        CATransaction.begin()
                        CATransaction.setValue(true, kCATransactionDisableActions)
                        layer.setFrame(frame)
                        cameraPreviewLayer.setFrame(frame)
                        CATransaction.commit()
                    }
                }
                cameraContainer.layer.addSublayer(cameraPreviewLayer)
                cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
                captureSession.startRunning()
                cameraContainer
            }
        )
    } else {
        Box(modifier, contentAlignment = Alignment.Center) {
            Text(
                """
            Camera is not available on simulator.
            Please try to run on a real iOS device.
        """,
                textAlign = TextAlign.Center
            )
        }
    }
}