package mainFlow.ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import loading.ui.LoadingUI
import mainFlow.components.MainFlowComponent

//@OptIn(
//    ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class,
//    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
//)

// MainFlowUI -> MainFlowScreen -> MainFlowContent
@Composable
fun SharedTransitionScope.MainFlowUI(
    component: MainFlowComponent
) {
    MainFlowScreen(component)
    LoadingUI(component.loadingComponent)
}