package mainFlow.ui.topBar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import dev.chrisbanes.haze.HazeState
import utils.SpacerH
import view.consts.Paddings
import widgets.glass.GlassCard

@Composable
internal fun SearchBar(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    isTitle: Boolean,
    focusRequester: FocusRequester,
    interactionSource: MutableInteractionSource,
    query: String,
    onChange: (String) -> Unit,
    onIconModeClick: () -> Unit
) {
    val textStyle = typography.bodyLarge

    val focusManager = LocalFocusManager.current

    GlassCard(
        modifier = modifier.then(
            if (isTitle) Modifier.clickable {
                onIconModeClick()
            }
            else Modifier
        ),
        hazeState = hazeState,
        isReversedProgressive = true,
    ) {
        AnimatedContent(
            isTitle,
            modifier = Modifier.animateContentSize(),
            {
                (fadeIn(animationSpec = tween(220)) +
                        scaleIn(initialScale = 0.92f, animationSpec = tween(220)))
                    .togetherWith(fadeOut(animationSpec = tween(90)))
            }
        ) { isIconMode ->

            Box(
                Modifier.fillMaxHeight().then(
                    if (isIconMode) {
                        Modifier.aspectRatio(1f)
                    } else Modifier.fillMaxWidth()
                )
            ) {
                if (!isIconMode) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(Icons.Rounded.Search, contentDescription = null)
                        SpacerH(Paddings.small)
                        BasicTextField(
                            value = query,
                            onValueChange =
                                onChange,
                            keyboardActions = KeyboardActions(onSearch = {
                                focusManager.clearFocus()
                            }),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            interactionSource = interactionSource,
                            modifier = Modifier.fillMaxWidth().weight(1f, false)
                                .focusRequester(focusRequester),
                            textStyle = textStyle.copy(color = colorScheme.onSurface), // ...
                            decorationBox = { innerTextField ->
                                Box(
                                    Modifier.width(IntrinsicSize.Max),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (query.isEmpty()) {
                                        Text(
                                            text = "Поиск",
                                            style = textStyle.copy(fontWeight = FontWeight.Medium),
                                            color = colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            cursorBrush = SolidColor(colorScheme.primary),
                            maxLines = 1,
                            singleLine = true
                        )
                        Icon(
                            Icons.Rounded.Mic,
                            contentDescription = null,
                            modifier = Modifier.clip(CircleShape).clickable {}
                        )
                    }
                } else {
                    Box(
                        Modifier.matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.Search,
                            modifier = Modifier.matchParentSize().scale(1.2f),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}