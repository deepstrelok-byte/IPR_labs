package utils

import androidx.annotation.IntRange
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image

actual suspend fun androidx.compose.ui.graphics.ImageBitmap.encodeToByteArray(
    @IntRange(
        from = 0,
        to = 100
    ) quality: Int
): ByteArray {
    val bitmap = this@encodeToByteArray.asSkiaBitmap()
    val imageFormat = EncodedImageFormat.JPEG
    return Image
        .makeFromBitmap(bitmap)
        .encodeToData(imageFormat, quality)
        ?.bytes ?: ByteArray(0)
}