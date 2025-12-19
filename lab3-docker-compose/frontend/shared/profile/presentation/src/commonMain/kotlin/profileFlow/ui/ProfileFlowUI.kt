package profileFlow.ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import animations.iosSlide
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import myProfile.ui.MyProfileUI
import profileFlow.components.ProfileFlowComponent
import transactions.ui.TransactionsUI

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun ProfileFlowUI(
    component: ProfileFlowComponent
) {
    val stack by component.stack.subscribeAsState()
    Surface(Modifier.fillMaxSize()) {
        Children(
            stack = stack,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                fallbackAnimation = stackAnimation(iosSlide()),
                onBack = component::onBackClicked
            )
        ) {

            when (val child = it.instance) {
                is ProfileFlowComponent.Child.MyProfileChild -> MyProfileUI(component = child.myProfileComponent)
                is ProfileFlowComponent.Child.TransactionsChild -> TransactionsUI(component = child.transactionsComponent)
            }
        }
    }
}