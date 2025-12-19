package widgets.textField

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import utils.SpacerH
import view.consts.Paddings
import view.consts.Sizes


object SurfaceTextFieldDefaults {
    val textFieldModifier = Modifier.minimumInteractiveComponentSize().fillMaxWidth()
        .defaultMinSize(minHeight = Sizes.minTextFieldHeight)
}

@Composable
fun SurfaceTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    paddings: PaddingValues = PaddingValues.Zero,
    textFieldModifier: Modifier = SurfaceTextFieldDefaults.textFieldModifier,
    shape: Shape = RoundedCornerShape(20.dp),
    singleLine: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholderText: String? = null,
    icon: ImageVector? = null,
    imeAction: ImeAction = ImeAction.Unspecified,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    inputTransformation: InputTransformation? = null,
    readOnly: Boolean = false
) {

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    var prevCursorRect: Rect? = remember { null }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val isPlaceholderInField = state.text.isEmpty() && !isFocused

    Box(Modifier.padding(paddings)) {


        Surface(
            modifier = modifier.padding(top = with(LocalDensity.current) { typography.labelSmall.fontSize.toDp() } + 2.dp),
            shape = shape,
            color = colorScheme.surfaceContainerHigh,
        ) {
            BasicTextField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = imeAction,
                    keyboardType = keyboardType
                ),
                readOnly = readOnly,

                modifier = textFieldModifier.bringIntoViewRequester(bringIntoViewRequester),
                state = state,
                cursorBrush = SolidColor(colorScheme.primary),
                lineLimits = if (singleLine) TextFieldLineLimits.SingleLine else TextFieldLineLimits.Default,
                textStyle = textStyle.copy(color = colorScheme.onSurface), // wtf? idk
                decorator = { innerTextField ->
                    Row(
                        modifier = Modifier.width(IntrinsicSize.Max)
                            .padding(
                                horizontal = Paddings.semiMedium,
                                vertical = Paddings.small
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        icon?.let {
                            Icon(icon, null)
                            SpacerH(Paddings.small)
                        }
                        Box(
                            Modifier,
                            contentAlignment = Alignment.CenterStart
                        ) {
                            placeholderText?.let { text ->
                                AnimatedPlaceholder(
                                    isVisible = isPlaceholderInField,
                                    text = text,
                                    textStyle = textStyle,
                                    alpha = .8f,
                                    isWithBackground = false
                                )
                            }
                            innerTextField()
                        }
                    }
                },
                inputTransformation = inputTransformation,
                onTextLayout = {
                    val flow = it.asFlow()
                    coroutineScope.launch {
                        flow.collect { textLayoutResult ->
                            textLayoutResult?.let {
                                val cursorRect =
                                    with(textLayoutResult.getCursorRect(state.selection.start)) {
                                        this.copy(bottom = this.bottom + 240f) // some padding
                                    }
                                if (prevCursorRect != cursorRect && isFocused) {
                                    prevCursorRect = cursorRect
                                    bringIntoViewRequester.bringIntoView(
                                        cursorRect
                                    )
                                }

                            }

                        }
                    }

                },
                interactionSource = interactionSource,

                )
        }
        placeholderText?.let { text ->

            AnimatedPlaceholder(
                isVisible = !isPlaceholderInField,
                text = text,
                textStyle = textStyle,
                alpha = 1f,
                modifier = Modifier.padding(start = Paddings.small),
                isWithBackground = true
            )
        }
    }
}

@Composable
private fun AnimatedPlaceholder(
    isVisible: Boolean,
    isWithBackground: Boolean,
    text: String,
    textStyle: TextStyle,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    // workaround laggy drop shadow shape ... (during animation there is no shape 0_o)
    var isAnimationEnded by remember { mutableStateOf(false) }
    val animatedAlpha by animateFloatAsState(
        if (isAnimationEnded) .2f else 0f,
        animationSpec = tween(300)
    )
    Column {
        AnimatedVisibility(isVisible) {
            isAnimationEnded =
                isWithBackground && this.transition.currentState == EnterExitState.Visible

            Text(
                text = text,
                style = textStyle
                    .copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = if (isWithBackground) typography.labelSmall.fontSize else textStyle.fontSize
                    ),
                color = colorScheme.onSurfaceVariant.copy(alpha = alpha),
                maxLines = 1,
                modifier = modifier.then(
                    if (isWithBackground) Modifier
                        .dropShadow(shapes.medium) {
                            radius = 10f
                            spread = 1f
                            offset = Offset(x = 0f, y = 5f)
                            this.alpha = animatedAlpha

                        }
                        .clip(shapes.medium)
                        .background(colorScheme.surfaceContainerHigh)
                        .padding(horizontal = Paddings.small)
                    else Modifier
                )
            )
        }
    }
}