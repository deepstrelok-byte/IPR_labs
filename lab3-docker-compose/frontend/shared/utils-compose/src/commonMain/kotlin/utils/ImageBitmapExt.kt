package utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.annotation.IntRange

expect suspend fun ImageBitmap.encodeToByteArray(
    @IntRange(from = 0, to = 100) quality: Int = 100
): ByteArray