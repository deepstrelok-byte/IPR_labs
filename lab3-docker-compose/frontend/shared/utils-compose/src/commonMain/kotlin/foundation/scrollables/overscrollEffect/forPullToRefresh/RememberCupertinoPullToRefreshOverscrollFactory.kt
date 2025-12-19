package foundation.scrollables.overscrollEffect.forPullToRefresh

import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.OverscrollFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity

@Composable
fun rememberCupertinoPullToRefreshOverscrollFactory(): OverscrollFactory {
    val density = LocalDensity.current

    val factory =
        remember {
            object : OverscrollFactory {
                override fun createOverscrollEffect(): OverscrollEffect =
                    CupertinoPullToRefreshOverscrollEffect(density.density, false)

                override fun equals(other: Any?): Boolean = other === this

                override fun hashCode(): Int = -1
            }
        }

    return factory
}