package common.grid

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalCurrentContentType: ProvidableCompositionLocal<ContentType?> =
    staticCompositionLocalOf {
        error("No LocalCurrentContentType provided")
    }