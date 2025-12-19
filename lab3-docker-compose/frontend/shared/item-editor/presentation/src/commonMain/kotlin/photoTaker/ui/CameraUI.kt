package photoTaker.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import architecture.launchIO
import camera.CameraCallback
import camera.CameraEvent
import camera.CameraPreview
import common.ImagesRow
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import photoTaker.components.PhotoTakerComponent
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import view.consts.Sizes
import widgets.glass.BackGlassButton
import widgets.glass.GlassCard
import widgets.glass.GlassCardFunctions


@Composable
internal fun CameraUI(
    component: PhotoTakerComponent
) {
    val images by component.pickedPhotos.collectAsState(Dispatchers.Main.immediate)


    val coroutineScope = rememberCoroutineScope()
    val photosRowLazyState = rememberLazyListState()

    val cameraCallback = remember {
        CameraCallback {
            component.onPhotoPick(it)
            coroutineScope.launch {
                if (photosRowLazyState.layoutInfo.totalItemsCount != 0) {
                    photosRowLazyState.animateScrollToItem(0)
                }
            }
        }
    }

    val safeContentPaddings = WindowInsets.safeContent.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()
    val bottomPadding = safeContentPaddings.calculateBottomPadding()

    val bottomBarPadding =
        (if (bottomPadding > Paddings.semiLarge) bottomPadding
        else Paddings.semiLarge) + Paddings.huge


    val hazeState = rememberHazeState()


    Box(Modifier.fillMaxSize()) {
        CameraPreview(
            modifier = Modifier.hazeSource(hazeState).fastBackground(Color.Black).fillMaxSize(),
            cameraCallback = cameraCallback
        )

        Box(Modifier.padding(top = topPadding + Paddings.small, start = Paddings.listHorizontalPadding)) {
            BackGlassButton(modifier = Modifier, hazeState = hazeState) {
                component.goBack()
            }
        }

        Column(
            Modifier.align(Alignment.BottomCenter).padding(bottom = bottomBarPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ImagesRow(
                photosRowLazyState = photosRowLazyState,
                images = images.asReversed(),
                isReversedNumeric = true,
                onRotateClick = component::rotatePhoto,
                onDeleteClick = component::deletePhoto
            )
            SpacerV(Paddings.medium)
            val height = Sizes.hugeCircularButton
            Box(
                modifier = Modifier.padding(horizontal = Paddings.medium)
                    .height(height)
            ) {
                Crossfade(
                    images.size < 5,
                    modifier = Modifier.fillMaxWidth().height(height),
                    animationSpec = tween(700)
                ) { isCanDoPhoto ->
                    Box(Modifier.fillMaxWidth()) {
                        if (isCanDoPhoto) {
                            BarButton(
                                modifier = Modifier.align(Alignment.CenterStart),
                                hazeState,
                                Icons.Rounded.Photo,
                                color = colorScheme.primaryContainer
                            ) {}
                            BarButton(
                                modifier = Modifier.align(Alignment.Center),
                                hazeState,
                                Icons.Rounded.CameraAlt,
                                color = colorScheme.secondaryContainer
                            ) {
                                if (images.size < 5) {
                                    coroutineScope.launchIO {
                                        cameraCallback.sendEvent(CameraEvent.CaptureImage)
                                    }
                                }
                            }
                        } else {
                            GlassCard(
                                hazeState = hazeState, modifier = Modifier.height(height).align(
                                    Alignment.CenterStart
                                ),
                                contentAlignment = Alignment.Center,
                                contentColor = Color.White
                            ) {
                                Text("Максимум 5 фото")
                            }
                        }
                    }
                }
                Crossfade(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    targetState = images.isNotEmpty()
                ) { isShown ->
                    if (isShown) {
                        BarButton(
                            hazeState = hazeState,
                            icon = Icons.Rounded.Done,
                            color = colorScheme.tertiaryContainer
                        ) {
                            component.goBack()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BarButton(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = modifier.size(Sizes.hugeCircularButton).clickable {
            onClick()
        },
        hazeState = hazeState,
        shape = CircleShape,
        hazeTint = GlassCardFunctions.getHazeTintColor(
            tint = null,
            containerColor = color,
            containerColorAlpha = .7f
        ),
        contentColor = Color.White,
    ) {
        Icon(
            icon, null,
            modifier = Modifier.fillMaxSize()
        )
    }
}