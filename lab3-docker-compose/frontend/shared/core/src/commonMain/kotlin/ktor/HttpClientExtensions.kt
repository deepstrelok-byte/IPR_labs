package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import network.NetworkState

inline fun <reified T> HttpClient.defaultPost(
    path: String,
    tokenProvider: TokenProvider,
    body: Any? = null,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Flow<NetworkState<T>> = defaultRequest(
    response = {
        val token = tokenProvider.getToken()
        post {
            url {
                token?.let { bearerAuth(token) }
                contentType(ContentType.Application.Json)
                path(path)
                body?.let { setBody(it) }
                block()
            }
        }
    }
)

inline fun <reified T> HttpClient.defaultPatch(
    path: String,
    tokenProvider: TokenProvider,
    body: Any? = null,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Flow<NetworkState<T>> = defaultRequest(
    response = {
        val token = tokenProvider.getToken()
        patch {
            url {
                token?.let { bearerAuth(token) }
                contentType(ContentType.Application.Json)
                path(path)
                body?.let { setBody(it) }
                block()
            }
        }
    }
)

inline fun <reified T> HttpClient.defaultDelete(
    path: String,
    tokenProvider: TokenProvider,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Flow<NetworkState<T>> = defaultRequest(
    response = {
        val token = tokenProvider.getToken()
        delete {
            url {
                token?.let { bearerAuth(token) }
                contentType(ContentType.Application.Json)
                path(path)
                block()
            }
        }
    }
)

inline fun <reified T> HttpClient.defaultGet(
    path: String,
    tokenProvider: TokenProvider,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): Flow<NetworkState<T>> = defaultRequest(
    response = {
        val token = tokenProvider.getToken()
        get {
            url {
                token?.let { bearerAuth(token) }
                contentType(ContentType.Application.Json)
                path(path)
                block()
            }
        }
    }
)


inline fun <reified T> HttpClient.defaultRequest(
    crossinline response: suspend () -> HttpResponse
): Flow<NetworkState<T>> = flow {
    emit(NetworkState.Loading())

    try {
        val response = response()

        when {
            response.status.isSuccess() -> {
                val responseBody = response.body<T>()
                emit(NetworkState.Success(responseBody))
            }

            else -> {
                emit(
                    NetworkState.Error(
                        Throwable("${response.status}: ${response.bodyAsText()}"),
                        // is there another way?
                        prettyPrint = response.bodyAsText().removePrefix("{\"detail\":\"")
                            .removeSuffix("\"}"),
                        code = response.status.value,
                    )
                )
            }
        }
    } catch (e: Exception) {
        // code == 0 -> Ошибку прислал не сервер
        emit(NetworkState.Error(e, "Что-то пошло не так", code = 0))
    }
}.flowOn(Dispatchers.IO)