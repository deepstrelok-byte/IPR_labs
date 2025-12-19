package itemManager.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import common.ImagesRow
import utils.SpacerV
import view.consts.Paddings
import widgets.sections.SectionTitle


@Composable
internal fun ItemImagesSection(
    images: List<ImageBitmap>,
    onAddButtonClick: () -> Unit,
    onDeleteClick: (ImageBitmap) -> Unit,
    onRotateClick: (ImageBitmap) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        SectionTitle("Фотографии")
        SpacerV(Paddings.small)
        ImagesRow(
            addButton = onAddButtonClick,
            images = images,
            onDeleteClick = onDeleteClick,
            onRotateClick = onRotateClick,
            isReversedNumeric = false
        )
    }
}