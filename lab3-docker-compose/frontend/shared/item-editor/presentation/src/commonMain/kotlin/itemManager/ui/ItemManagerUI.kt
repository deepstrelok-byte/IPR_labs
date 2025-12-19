package itemManager.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import foundation.scrollables.VerticalScrollableBox
import itemManager.components.ItemManagerComponent
import itemManager.ui.sections.AIHelpSection
import itemManager.ui.sections.DefaultInformationSection
import itemManager.ui.sections.DeliveryTypesSection
import itemManager.ui.sections.ItemImagesSection
import kotlinx.coroutines.Dispatchers
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import widgets.sections.CreateButtonSection

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
internal fun ItemManagerUI(
    component: ItemManagerComponent
) {
    val createOrEditItemResult by component.createOrEditItemResult.collectAsState()

    val windowInsets = WindowInsets.safeContent.exclude(WindowInsets.ime)
    val safeContentPaddings = windowInsets.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()
    var scaffoldMaxTopPadding by remember { mutableStateOf(Paddings.small) }

    val hazeState = rememberHazeState()
    val scrollState = rememberScrollState()

    val images by component.photoTakerComponent.pickedPhotos.collectAsState(Dispatchers.Main.immediate)


    val title = component.title
    val description = component.description
    val itemCategory by component.itemCategory.collectAsState(Dispatchers.Main.immediate)
    val pickedDeliveryTypes by component.deliveryTypes.collectAsState(Dispatchers.Main.immediate)

    val itemManagerPreData = component.itemManagerPreData

    val aiAnswer by component.aiAnswer.collectAsState()

    Scaffold(
        Modifier.fillMaxSize().imePadding(),
        topBar = {
            TopBar(
                isEditing = component.isEditing,
                isVisible = !scrollState.canScrollBackward,
                topPadding = topPadding,
                hazeState = hazeState,
                onBackClick = component.closeFlow,
            )
        }
    ) { scaffoldPaddings ->
        scaffoldMaxTopPadding =
            maxOf(scaffoldMaxTopPadding, scaffoldPaddings.calculateTopPadding())
        VerticalScrollableBox(
            modifier = Modifier.fillMaxSize()
                .fastBackground(colorScheme.background)
                .hazeSource(hazeState),
            windowInsets = windowInsets,
            scrollState = scrollState,
            bottomPaddingExtraHeight = Paddings.enormous
        ) {
            Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                SpacerV(scaffoldMaxTopPadding - topPadding + Paddings.medium)
                if (!component.isEditing) {
                    ItemImagesSection(
                        images = images,
                        onAddButtonClick = {
                            if (!createOrEditItemResult.isLoading()) {
                                component.openPhotoTakerComponent()
                            }
                        },
                        onDeleteClick = {
                            if (!createOrEditItemResult.isLoading()) {
                                component.photoTakerComponent.deletePhoto(it)
                            }
                        },
                        onRotateClick = {
                            if (!createOrEditItemResult.isLoading()) {
                                component.photoTakerComponent.rotatePhoto(it)
                            }
                        }
                    )

                    SpacerV(Paddings.medium)

                    AIHelpSection(
                        images.isNotEmpty(),
                        aiAnswer = aiAnswer,
                        curTitle = title.text.toString(),
                        curDescription = description.text.toString(),
                        curCategory = itemCategory,
                        onHelpClick = component::askAI,
                        onAcceptTitleClick = { title.setTextAndPlaceCursorAtEnd(it) },
                        onAcceptDescriptionClick = { description.setTextAndPlaceCursorAtEnd(it) },
                        onAcceptItemCategoryClick = component::updateItemCategory
                    )

                    SpacerV(Paddings.medium)
                }

                DefaultInformationSection(
                    titleState = title,
                    descState = description,
                    itemCategory = itemCategory,
                    preCategory = if (component.isEditing) null else itemManagerPreData.category,
                    readOnly = createOrEditItemResult.isLoading()
                ) {
                    component.updateItemCategory(it)
                }
                SpacerV(Paddings.medium)
                DeliveryTypesSection(
                    pickedDeliveryTypes,
                    availableDeliveryTypes = itemManagerPreData.availableDeliveryTypes
                ) { item ->
                    component.updateDeliveryType(item)
                }
                SpacerV(Paddings.medium)

                val allFieldsFilled = title.text.isNotBlank()
                        && description.text.isNotBlank()
                        && (images.isNotEmpty() || component.isEditing)
                        && itemCategory != null
                        && pickedDeliveryTypes.isNotEmpty()

                val smthChanged = (
                        title.text.toString() != itemManagerPreData.title ||
                                description.text.toString() != itemManagerPreData.description ||
                                itemCategory != itemManagerPreData.category ||
                                pickedDeliveryTypes != itemManagerPreData.deliveryTypes
                        )

                CreateButtonSection(
                    enabled = allFieldsFilled && smthChanged,
                    isLoading = createOrEditItemResult.isLoading(),
                    text = if (component.isEditing) "Обновить предмет" else "Выложить предмет",
                    haveToText = if (!smthChanged) "Необходимо изменить хотя бы 1 поле" else "Небходимо заполнить все поля"
                ) {
                    component.createOrEditItem()
                }

            }


        }
    }
}