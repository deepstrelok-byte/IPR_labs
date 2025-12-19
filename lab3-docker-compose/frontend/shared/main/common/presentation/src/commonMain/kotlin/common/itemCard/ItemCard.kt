package common.itemCard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import architecture.launchIO
import common.itemDetailsTransition.ItemDetailsAnimator
import common.itemDetailsTransition.SharedAnimation
import itemDetails.ui.bottomSheet.isExpanded
import utils.SpacerV
import utils.fastBackground
import view.consts.Paddings
import widgets.SimpleChip


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemCard(
    modifier: Modifier = Modifier,
    title: String,
    id: String,
    key: String,
    imagePath: String,
    location: String,
    itemDetailsAnimator: ItemDetailsAnimator,
    isMyCard: Boolean,
    onClicked: () -> Unit
) {
    val cardShape = RoundedCornerShape(ItemCardDefaults.cardShapeDp)

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()


    val isAnimating = itemDetailsAnimator.detailedItemKey != key

    Card(
        modifier = modifier
            .clip(cardShape)
            .bringIntoViewRequester(bringIntoViewRequester)
            .clickable {
                coroutineScope.launchIO {
                    bringIntoViewRequester.bringIntoView()
                }

                onClicked()
            },
        shape = cardShape
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(ItemCardDefaults.containerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box() {
                if (isAnimating) {
                    ItemImage(
                        imagePath = imagePath,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.1f),
                        key = key,
                        detailedItemKey = itemDetailsAnimator.detailedItemKey,
                        animatedContentScope = null
                    )
                } else {
                    itemDetailsAnimator.SharedAnimation(
                        modifier = Modifier.fillMaxWidth().aspectRatio(1.1f)
                    ) { sheetValue ->
                        if (!sheetValue.isExpanded()) {
                            ItemImage(
                                imagePath = imagePath,
                                modifier = Modifier
                                    .fillMaxSize(),
                                key = key,
                                detailedItemKey = itemDetailsAnimator.detailedItemKey,
                                animatedContentScope = this
                            )
                        }
                    }
                }
                if (isMyCard) {
                    Row {
                        AnimatedVisibility(isAnimating) {
                            SimpleChip(
                                text = "Ваше",
                                modifier = Modifier.padding(Paddings.ultraSmall)
                                    .clip(shapes.small)
                                    .fastBackground(Color.Black.copy(alpha = .7f))
                                    .alpha(.7f),
                                textColor = Color.White,
                                borderColor = Color.White
                            )
                        }
                    }
                }
            }

            SpacerV(Paddings.semiSmall)

            Text(
                title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = typography.bodyMediumEmphasized,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium
            )

            Text(
                location,
                maxLines = 1,
                style = typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.alpha(.7f),
            )
        }
    }
}
