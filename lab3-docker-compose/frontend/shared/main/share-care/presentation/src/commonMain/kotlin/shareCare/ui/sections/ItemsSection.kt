package shareCare.ui.sections

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import common.detailsInterfaces.DetailsConfig
import common.grid.ColumnHeader
import common.grid.ContentType
import common.grid.defaults.DefaultItemsContent
import common.itemDetailsTransition.ItemDetailsAnimator
import entities.ShareCareItems
import network.NetworkState

@Suppress("FunctionName")
internal fun LazyGridScope.ItemsSection(
    items: NetworkState<ShareCareItems>,
    sharedTransitionScope: SharedTransitionScope,
    itemDetailsAnimator: ItemDetailsAnimator,
    onClick: (DetailsConfig.ItemDetailsConfig) -> Unit,
    refreshClick: () -> Unit
) {
    val data = items.data

    if (data != null) {
        val responses = data.responses
        val myPublishedItems = data.myPublishedItems

        if (responses.isNotEmpty()) {
            DefaultItemsContent(
                items = responses,
                contentType = ContentType.Responses,
                sharedTransitionScope = sharedTransitionScope,
                itemDetailsAnimator = itemDetailsAnimator,
                onCardClicked = onClick,
                myId = null
            )
        }
        if (myPublishedItems.isNotEmpty()) {
            DefaultItemsContent(
                items = myPublishedItems,
                contentType = ContentType.MyItems,
                sharedTransitionScope = sharedTransitionScope,
                itemDetailsAnimator = itemDetailsAnimator,
                onCardClicked = onClick,
                myId = null
            )
        }
    }

    if (items is NetworkState.Error) {
        ColumnHeader(
            key = "itemsError",
            text = "Ошибка"
        ) {
            Text(
                items.prettyPrint,
                textAlign = TextAlign.Center,
            )
            Button(onClick = refreshClick) {
                Text("Ещё раз")
            }
        }
    }
}