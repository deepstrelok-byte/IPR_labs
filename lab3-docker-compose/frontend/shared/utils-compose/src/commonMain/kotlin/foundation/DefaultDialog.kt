package foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import platform.Platform
import platform.currentPlatform

@Composable
fun DefaultDialog(
    shape: Shape = shapes.extraLarge,
    dismissable: Boolean = true,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {

    val isCustomAnimation = currentPlatform != Platform.Android
    var isShowing by remember {
        mutableStateOf(!isCustomAnimation)
    }
    val animationDuration = 400
    val animatedAlpha by animateFloatAsState(if (isShowing) 1f else 0f, animationSpec = tween(animationDuration))

    LaunchedEffect(Unit) {
        isShowing = true
    }



    val coroutineScope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                if (isCustomAnimation) {
                    isShowing = false
                    delay(animationDuration.toLong())
                }
                onDismissRequest()
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = dismissable,
            dismissOnClickOutside = dismissable
        )
    ) {
        Surface(Modifier.fillMaxWidth().alpha(animatedAlpha), shape = shape) {
            content()
        }
    }
}