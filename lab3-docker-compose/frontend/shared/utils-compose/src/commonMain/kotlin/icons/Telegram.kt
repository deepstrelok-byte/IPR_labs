package icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import view.consts.Sizes

val Icons.Rounded.Telegram: ImageVector
    get() {
        if (_TelegramBrandsSolidFull != null) {
            return _TelegramBrandsSolidFull!!
        }
        _TelegramBrandsSolidFull = ImageVector.Builder(
            name = "TelegramBrandsSolidFull",
            defaultWidth = Sizes.iconSize,
            defaultHeight = Sizes.iconSize,
            viewportWidth = 640f,
            viewportHeight = 640f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(320f, 72f)
                curveTo(183f, 72f, 72f, 183f, 72f, 320f)
                curveTo(72f, 457f, 183f, 568f, 320f, 568f)
                curveTo(457f, 568f, 568f, 457f, 568f, 320f)
                curveTo(568f, 183f, 457f, 72f, 320f, 72f)
                close()
                moveTo(435f, 240.7f)
                curveTo(431.3f, 279.9f, 415.1f, 375.1f, 406.9f, 419f)
                curveTo(403.4f, 437.6f, 396.6f, 443.8f, 390f, 444.4f)
                curveTo(375.6f, 445.7f, 364.7f, 434.9f, 350.7f, 425.7f)
                curveTo(328.9f, 411.4f, 316.5f, 402.5f, 295.4f, 388.5f)
                curveTo(270.9f, 372.4f, 286.8f, 363.5f, 300.7f, 349f)
                curveTo(304.4f, 345.2f, 367.8f, 287.5f, 369f, 282.3f)
                curveTo(369.2f, 281.6f, 369.3f, 279.2f, 367.8f, 277.9f)
                curveTo(366.3f, 276.6f, 364.2f, 277.1f, 362.7f, 277.4f)
                curveTo(360.5f, 277.9f, 325.6f, 300.9f, 258.1f, 346.5f)
                curveTo(248.2f, 353.3f, 239.2f, 356.6f, 231.2f, 356.4f)
                curveTo(222.3f, 356.2f, 205.3f, 351.4f, 192.6f, 347.3f)
                curveTo(177.1f, 342.3f, 164.7f, 339.6f, 165.8f, 331f)
                curveTo(166.4f, 326.5f, 172.5f, 322f, 184.2f, 317.3f)
                curveTo(256.5f, 285.8f, 304.7f, 265f, 328.8f, 255f)
                curveTo(397.7f, 226.4f, 412f, 221.4f, 421.3f, 221.2f)
                curveTo(423.4f, 221.2f, 427.9f, 221.7f, 430.9f, 224.1f)
                curveTo(432.9f, 225.8f, 434.1f, 228.2f, 434.4f, 230.8f)
                curveTo(434.9f, 234f, 435f, 237.3f, 434.8f, 240.6f)
                close()
            }
        }.build()

        return _TelegramBrandsSolidFull!!
    }

@Suppress("ObjectPropertyName")
private var _TelegramBrandsSolidFull: ImageVector? = null
