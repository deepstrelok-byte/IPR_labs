package common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Rotate90DegreesCw
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import utils.SpacerH
import utils.fastBackground
import view.consts.Paddings

@Composable
internal fun ImagesRow(
    addButton: (() -> Unit)? = null,
    photosRowLazyState: LazyListState = rememberLazyListState(),
    images: List<ImageBitmap>,
    isReversedNumeric: Boolean = false,
    onDeleteClick: (ImageBitmap) -> Unit,
    onRotateClick: (ImageBitmap) -> Unit
) {
    val density = LocalDensity.current
    val containerSize = LocalWindowInfo.current.containerSize
    val photoWidth = remember(containerSize.width) {
        with(density) { (containerSize.width / 3).toDp() }
    }
    val aspectRatio = .9f

    LazyRow(
        Modifier.animateContentSize().fillMaxWidth(),
        horizontalArrangement = if (addButton == null) Arrangement.Center else Arrangement.Start,
        state = photosRowLazyState,
    ) {

        item {
            if (addButton == null) {
                SpacerH(Paddings.big)
            } else {
                if (images.size < 5) {
                    Box(
                        Modifier.padding(
                            start = Paddings.listHorizontalPadding,
                            end = Paddings.small
                        ).width(photoWidth).aspectRatio(aspectRatio).clip(shapes.large)
                            .fastBackground(colorScheme.surfaceContainer).clickable {
                                addButton()
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Rounded.Add, null)
                    }
                }
            }
        }
        itemsIndexed(items = images, key = { i, x -> x.hashCode() }) { index, image ->
            Box(Modifier.animateItem()) {
                Image(
                    image, contentDescription = null,
                    modifier = Modifier.padding(horizontal = Paddings.small)
                        .width(photoWidth).aspectRatio(aspectRatio)
                        .clip(shapes.large),
                    contentScale = ContentScale.Crop
                )
                Box(
                    Modifier.align(Alignment.TopEnd).padding(end = Paddings.small)
                        .clip(CircleShape)
                        .fastBackground(Color.Black.copy(alpha = .7f))
                        .clickable {
                            onDeleteClick(image)
                        }
                ) {
                    Icon(Icons.Rounded.Close, null, tint = Color.White)
                }
                Box(Modifier.matchParentSize()) {
                    Row(
                        Modifier.fillMaxWidth().align(Alignment.BottomStart)
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            Modifier.fillMaxHeight().padding(start = Paddings.small)
                                .clip(CircleShape)
                                .fastBackground(Color.Black.copy(alpha = .7f))
                                .clickable {
                                    onRotateClick(image)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Rounded.Rotate90DegreesCw,
                                modifier = Modifier.padding(horizontal = Paddings.small),
                                tint = Color.White,
                                contentDescription = "Rotate"
                            )
                        }

                        Box(
                            Modifier.padding(end = Paddings.small)
                                .clip(CircleShape)
                                .fastBackground(Color.Black.copy(alpha = .7f))
                                .clickable {
                                    onDeleteClick(image)
                                }
                        ) {
                            Text(
                                (if (!isReversedNumeric) index + 1 else images.size - index).toString(),
                                modifier = Modifier.padding(horizontal = Paddings.small),
                                color = Color.White
                            )
                        }
                    }


                }
            }
        }
        item {
            SpacerH(Paddings.big)
        }
    }
}