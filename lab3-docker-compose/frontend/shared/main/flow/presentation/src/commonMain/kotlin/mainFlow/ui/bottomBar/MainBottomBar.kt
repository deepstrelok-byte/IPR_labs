package mainFlow.ui.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import careshare.shared.main.flow.presentation.generated.resources.Res
import careshare.shared.main.flow.presentation.generated.resources.bar_find_help
import careshare.shared.main.flow.presentation.generated.resources.bar_share_care
import dev.chrisbanes.haze.HazeState
import foundation.layouts.ThreeComponentsLayout
import kotlinx.coroutines.launch
import mainFlow.components.MainFlowComponent.Child
import mainFlow.components.MainFlowComponent.Config
import utils.value
import view.consts.Paddings

@Composable
internal fun MainBottomBar(
    modifier: Modifier,
    isVerified: Boolean,
    child: Child,
    hazeState: HazeState,
    navigateTo: (Config) -> Unit,
    goToProfile: () -> Unit,
    onFABButtonClick: (Boolean) -> Unit,
    lazyGridStateFindHelp: LazyGridState,
    lazyGridStateShareCare: LazyGridState
) {
    val coroutineScope = rememberCoroutineScope()
    val isFindHelp = child is Child.FindHelpChild

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isFindHelp && !isVerified) {
            Text(
                "Необходимо верифицировать аккаунт",
                modifier = Modifier
                    .padding(bottom = Paddings.small)
                    .clip(shapes.medium)
                    .background(colorScheme.inverseSurface)
                    .padding(horizontal = Paddings.small, vertical = Paddings.semiSmall),
                style = typography.labelMedium,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = colorScheme.inverseOnSurface
            )
        }
        ThreeComponentsLayout(
            modifier = modifier,
            isCenter = true,
            isSpaceAbove = true,
            paddingBetween = Paddings.semiSmall,
            leftContent = {
                BottomBarButton(
                    isSelected = isFindHelp,
                    selectedColor = colorScheme.secondaryContainer,
                    text = Res.string.bar_find_help.value,
                    icon = Icons.Rounded.Interests,
                    hazeState = hazeState,
                    onLongClick = {
                        coroutineScope.launch {
                            lazyGridStateFindHelp.animateScrollToItem(0)
                        }
                    }
                ) {
                    navigateTo(Config.FindHelp)
                }
            },
            centerContent = {
                MainFAB(
                    hazeState = hazeState,
                    isFindHelp = isFindHelp,
                    isVerified = isVerified
                ) {
                    if (isFindHelp && !isVerified) {
                        goToProfile()
                    } else {
                        onFABButtonClick(isFindHelp)
                    }
                }
            },
            rightContent = {
                BottomBarButton(
                    isSelected = !isFindHelp,
                    selectedColor = colorScheme.tertiaryContainer,
                    text = Res.string.bar_share_care.value,
                    icon = Icons.Rounded.VolunteerActivism,
                    hazeState = hazeState,
                    onLongClick = {
                        coroutineScope.launch {
                            lazyGridStateShareCare.animateScrollToItem(0)
                        }
                    }
                ) {
                    navigateTo(Config.ShareCare)
                }
            }
        )
    }
}

