package ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import network.NetworkConfig


@OptIn(ExperimentalSerializationApi::class)
internal fun createHttpClient(
    enableLogging: Boolean
): HttpClient {
    return HttpClient(CIO) {

        if (enableLogging) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }


        install(HttpTimeout) {
            connectTimeoutMillis = 15000
            requestTimeoutMillis = 30000
        }

        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }, contentType = ContentType.Any
            )
        }



        defaultRequest {
            header("Content-Type", "application/json; charset=UTF-8")

            contentType(ContentType.Application.Json)
            url {
                host = NetworkConfig.host
                protocol = URLProtocol.HTTP
            }
            port = NetworkConfig.port
        }
    }
}