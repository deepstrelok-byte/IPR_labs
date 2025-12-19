package photoTaker.components

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RealPhotoTakerComponent(
    componentContext: ComponentContext,
    override val goBack: () -> Unit,
) : ComponentContext by componentContext, PhotoTakerComponent {


    override val pickedPhotos = MutableStateFlow<List<ImageBitmap>>(emptyList())

    override fun onPhotoPick(imageBitmap: ImageBitmap) {
        if (pickedPhotos.value.size < 5) {
            // IMPORTANT: реверсия, т.к. иначе скролл лагает (wtf)
            this.pickedPhotos.update { current ->
                listOf<ImageBitmap>() + current + imageBitmap
            }
        }
    }

    override fun deletePhoto(imageBitmap: ImageBitmap) {
        this.pickedPhotos.update { current -> current - imageBitmap }
    }

    override fun rotatePhoto(imageBitmap: ImageBitmap) {
        this.pickedPhotos.update { current ->
            current.map {
                if (it == imageBitmap) it.rotate()
                else it
            }
        }
    }
}

fun ImageBitmap.rotate(): ImageBitmap {
    // При повороте на 90 градусов ширина и высота меняются местами
    val newWidth = height
    val newHeight = width

    val newBitmap = ImageBitmap(newWidth, newHeight, config)
    val canvas = Canvas(newBitmap)
    val paint = Paint()
    canvas.save()

    // Поворот по часовой стрелке
    // Смещаемся вправо на ширину нового изображения
    canvas.translate(newWidth.toFloat(), 0f)
    canvas.rotate(90f)

    canvas.drawImageRect(
        image = this,
        srcOffset = IntOffset.Zero,
        srcSize = IntSize(width, height),
        dstOffset = IntOffset.Zero,
        dstSize = IntSize(width, height),
        paint = paint
    )

    canvas.restore()

    return newBitmap
}