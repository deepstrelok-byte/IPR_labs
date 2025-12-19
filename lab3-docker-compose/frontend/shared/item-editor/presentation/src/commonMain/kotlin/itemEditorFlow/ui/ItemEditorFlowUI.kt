package itemEditorFlow.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import itemEditorFlow.components.ItemEditorFlowComponent
import itemManager.ui.ItemManagerUI
import photoTaker.ui.PhotoTakerUI
import utils.fastBackground

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun ItemEditorFlowUI(
    component: ItemEditorFlowComponent
) {

    val stack by component.stack.subscribeAsState()
    Children(
        stack = stack,
        modifier = Modifier.fillMaxSize().fastBackground(colorScheme.background),
        animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            fallbackAnimation = stackAnimation(),
            onBack = component::onBackClicked
        )
    ) {
        when (val child = it.instance) {
            is ItemEditorFlowComponent.Child.PhotoTakerChild ->
                PhotoTakerUI(child.photoTakerComponent)

            is ItemEditorFlowComponent.Child.ItemManagerChild -> ItemManagerUI(child.itemManagerComponent)
        }
    }
}