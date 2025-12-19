package mainFlow.ui.topBar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import common.grid.ContentType
import dev.chrisbanes.haze.HazeState
import foundation.layouts.RightImportantLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mainFlow.components.MainFlowComponent
import utils.SpacerH
import view.consts.Paddings

@Composable
internal fun MainTopBar(
    component: MainFlowComponent,
    modifier: Modifier,
    hazeState: HazeState,
    currentContentType: ContentType?,
    searchBarQuery: String,
    onSearchBarChange: (String) -> Unit
) {


    val density = LocalDensity.current


    val searchBarInteractionSource = remember { MutableInteractionSource() }
    val isSearchBarFocused by searchBarInteractionSource.collectIsFocusedAsState()
    val searchBarFocusRequester = remember { FocusRequester() }
    var isFocusRequested by remember { mutableStateOf(false) }

    val isTitle =
        currentContentType != null
                && currentContentType != ContentType.Catalog && currentContentType != ContentType.PeopleSearch
                && !isSearchBarFocused && !isFocusRequested

    var maxHeight by remember { mutableStateOf(0.dp) }
    var width by remember { mutableStateOf(0.dp) }

    val paddingBetweenIcons = animateDpAsState(
        if (isTitle) Paddings.medium else Paddings.small,
        animationSpec = tween(400)
    )

    val coroutineScope = rememberCoroutineScope()

    RightImportantLayout(
        modifier = modifier.then(
            if (maxHeight > 0.dp) Modifier.height(maxHeight)
            else Modifier.height(IntrinsicSize.Min)
        ).onSizeChanged {
            with(density) {
                if (maxHeight.roundToPx() < it.height) maxHeight = it.height.toDp()
                width = it.width.toDp()
            }
        },
        leftSide = {
            Box() {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TopTitle(
                        modifier = Modifier.widthIn(max = width - 2 * (maxHeight + paddingBetweenIcons.value) - Paddings.listHorizontalPadding),
                        isTitle = isTitle,
                        hazeState = hazeState,
                        contentType = currentContentType
                    )
                    if (isTitle) {
                        SpacerH(paddingBetweenIcons.value)
                    } else {
                        SpacerH(Paddings.listHorizontalPadding)
                    }
                    SearchBar(
                        modifier = Modifier.then(if (isTitle) Modifier.widthIn(maxHeight) else Modifier),
                        hazeState = hazeState, isTitle = isTitle,
                        interactionSource = searchBarInteractionSource,
                        query = searchBarQuery,
                        onChange = onSearchBarChange,
                        focusRequester = searchBarFocusRequester
                    ) {
                        coroutineScope.launch {
                            isFocusRequested = true
                            // 220, т.к. столько идёт анимация появления textfield'а
                            delay(220) // TODO: проверить всегда ли работает, если лаги?
                            searchBarFocusRequester.requestFocus()
                            isFocusRequested = false
                        }
                    }
                }
            }

        }, rightSide = {
            SpacerH(paddingBetweenIcons.value)
            val isCloseButton = isSearchBarFocused || searchBarQuery.isNotEmpty()

            TopBarButton(
                hazeState = hazeState,
                isCloseButton = isCloseButton
            ) {
                if (isCloseButton) onSearchBarChange("")
                else component.output(MainFlowComponent.Output.NavigateToProfile(null, null, openVerification = false))
            }

            SpacerH(Paddings.listHorizontalPadding)
        }
    )
}