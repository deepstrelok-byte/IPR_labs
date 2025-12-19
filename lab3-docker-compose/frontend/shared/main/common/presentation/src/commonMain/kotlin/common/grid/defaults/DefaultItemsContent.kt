package common.grid.defaults

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.detailsInterfaces.DetailsConfig
import common.grid.ContentType
import common.itemCard.ItemCard
import common.itemDetailsTransition.ItemDetailsAnimator
import entity.ItemResponse

@Suppress("FunctionName")
fun LazyGridScope.DefaultItemsContent(
    items: List<ItemResponse>,
    contentType: ContentType,
    sharedTransitionScope: SharedTransitionScope,
    itemDetailsAnimator: ItemDetailsAnimator,
    myId: String?,
    onCardClicked: (DetailsConfig.ItemDetailsConfig) -> Unit
) {

    DefaultGridContent(
        items = items,
        key = { it.id },
        contentType = contentType,
        filters = null
    ) { item, key ->
        DefaultItemCard(
            item = item,
            key = key,
            itemDetailsAnimator = itemDetailsAnimator,
            myId = myId,
            sharedTransitionScope = sharedTransitionScope,
            onCardClicked = onCardClicked
        )
    }
}

@Composable
fun LazyGridItemScope.DefaultItemCard(
    item: ItemResponse,
    key: String,
    itemDetailsAnimator: ItemDetailsAnimator,
    myId: String?,
    sharedTransitionScope: SharedTransitionScope,
    onCardClicked: (DetailsConfig.ItemDetailsConfig) -> Unit
) {

    val imagePath = item.images.firstOrNull() ?: ""
    with(sharedTransitionScope) {
        ItemCard(
            modifier = Modifier
                .animateItem()
                .fillMaxSize(),
            title = item.title,
            id = item.id,
            key = key,
            imagePath = imagePath,
            location = item.location,
            itemDetailsAnimator = itemDetailsAnimator,
            isMyCard = myId == item.ownerId
        ) {
            onCardClicked(
                item.toConfig(key)
            )
        }
    }
}


fun ItemResponse.toConfig(key: String) = DetailsConfig.ItemDetailsConfig(
    id = this.id,
    images = this.images,
    creatorId = this.ownerId,
    title = this.title,
    description = this.description,
    location = this.location,
    category = this.category,
    deliveryTypes = this.deliveryTypes,
    recipientId = this.recipientId,
    telegram = this.telegram,
    key = key
)