package utils

import android.graphics.Bitmap
import androidx.annotation.IntRange
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

actual suspend fun androidx.compose.ui.graphics.ImageBitmap.encodeToByteArray(
    @IntRange(
        from = 0,
        to = 100
    ) quality: Int
): ByteArray {
    val bitmap = this@encodeToByteArray.asAndroidBitmap()
    val compressFormat = Bitmap.CompressFormat.JPEG
    return ByteArrayOutputStream().use { bytes ->
        bitmap.compress(compressFormat, quality, bytes)
        bytes.toByteArray()
    }
}