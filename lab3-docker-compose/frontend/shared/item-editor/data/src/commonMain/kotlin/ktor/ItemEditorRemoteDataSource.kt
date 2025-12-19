package ktor

import dto.AICreateHelpDTO
import dto.ItemDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import network.NetworkState

class ItemEditorRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {

    fun createItem(item: ItemDTO): Flow<NetworkState<Unit>> =
        hc.requestWithImages(
            tokenProvider = tokenProvider,
            url = ITEMS_PATH,
            images = item.images
        ) {
            append("item_data", Json.encodeToString(item.copy(images = emptyList())))
        }


    fun editItem(item: ItemDTO, itemId: String): Flow<NetworkState<Unit>> =
        hc.defaultPatch(ITEMS_PATH, tokenProvider, body = item) {
            parameter("item_id", itemId)
        }

    fun askAICreateHelp(images: List<ByteArray>): Flow<NetworkState<AICreateHelpDTO>> =
        hc.requestWithImages(tokenProvider = tokenProvider, url = ASK_AI_PATH, images = images) {}

    private companion object {
        const val ITEMS_PATH = "/items/"
        const val ASK_AI_PATH = "/api/v1/ml/analyze-images"
    }
}

private inline fun <reified T> HttpClient.requestWithImages(
    tokenProvider: TokenProvider,
    url: String,
    images: List<ByteArray>,
    crossinline extraBody: FormBuilder.() -> Unit
): Flow<NetworkState<T>> {
    val hc = this
    return hc.defaultRequest {
        val token = tokenProvider.getToken()
        hc.submitFormWithBinaryData(
            url = url,
            formData = formData {

                extraBody()

                // Добавляем изображения отдельно
                images.forEachIndexed { index, imageData ->
                    append(
                        "images",
                        imageData,
                        Headers.build {
                            append(
                                "Content-Disposition",
                                "form-data; name=\"images\"; filename=\"image$index.jpg\""
                            )
                            append("Content-Type", "image/jpeg")
                        }
                    )
                }
            }
        ) {
            contentType(ContentType.Application.Json)
            token?.let { bearerAuth(token) }
        }
    }
}