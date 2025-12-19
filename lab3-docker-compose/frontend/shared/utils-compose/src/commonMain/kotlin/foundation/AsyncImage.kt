package foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import careshare.shared.SharedRes
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade


@Composable
fun AsyncLocalImage(
    path: String,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        link = SharedRes.getUri(path),
        modifier = modifier,
        contentDescription = contentDescription,
        contentScale = contentScale,
        colorFilter = colorFilter
    )

}

@Composable
fun AsyncImage(
    link: String,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    key: String? = null,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    val model = ImageRequest.Builder(LocalPlatformContext.current)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED) // TODO
        .placeholderMemoryCacheKey(key) //  same key as shared element key
        .memoryCacheKey(key)
        .crossfade(true)
        .data(
            link
        ).build()

    AsyncImage(
        model = model,
        modifier = modifier,
        contentScale = contentScale,
        contentDescription = contentDescription,
        colorFilter = colorFilter
    )
}