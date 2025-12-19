package common.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import common.grid.ColumnHeader
import common.grid.ContentType
import common.grid.defaults.DefaultGridContent
import common.grid.parseName
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState
import view.consts.Paddings

@Suppress("FunctionName")
fun <T> LazyGridScope.SearchSection(
    searchResponse: NetworkState<List<T>>,
    searchData: SearchData,
    contentType: ContentType,
    onDeliveryTypesChange: (List<DeliveryType>) -> Unit,
    onCategoryChange: (ItemCategory?) -> Unit,
    hasMoreItems: Boolean,
    refreshClick: () -> Unit,
    key: (T) -> String,
    cardContent: @Composable LazyGridItemScope.(T, String) -> Unit,
) {

    val items = searchResponse.data
    if (items != null) {
        DefaultGridContent(
            items = items,
            key = key,
            contentType = contentType,
            filters = {
                Filters(
                    deliveryTypes = searchData.deliveryTypes,
                    category = searchData.category,
                    onDeliveryTypeClick = { clicked ->
                        if (clicked != null) {
                            val deliveryTypes = searchData.deliveryTypes.toMutableList()
                            if (clicked in deliveryTypes) deliveryTypes.remove(clicked)
                            else deliveryTypes.add(clicked)
                            onDeliveryTypesChange(deliveryTypes)
                        } else onDeliveryTypesChange(listOf())
                    },
                    onCategoryClick = { onCategoryChange(it) }
                )
            },
            cardContent = cardContent
        )
        if (items.isEmpty()) {
            item(key = "NoSearchItems", span = { GridItemSpan(maxLineSpan) }) {
                Text("Тут пусто")
            }
        }

    } else if (searchResponse is NetworkState.Error) {
        ColumnHeader(
            key = "searchError",
            text = contentType.parseName()
        ) {
            Text(
                "Не удалось загрузить объекты",
                textAlign = TextAlign.Center,
            )
            Button(onClick = refreshClick) {
                Text("Ещё раз")
            }
        }
    }


    if (hasMoreItems && searchResponse.isLoading()) {
        item(key = "moreSearchLoadAnimation", span = { GridItemSpan(maxLineSpan) }) {
            Box(
                Modifier.animateItem().fillMaxWidth().padding(top = Paddings.small),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        }
    }
}