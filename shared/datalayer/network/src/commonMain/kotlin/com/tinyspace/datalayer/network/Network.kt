package com.tinyspace.datalayer.network

import com.tinyspace.datalayer.network.model.VerifyPurchaseRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object Network {

    private val ktorClient: HttpClient
        get() = HttpClient(CIO) {
            install(UserAgent) {
                agent = "Ktor client"
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }


    suspend fun verify(request: VerifyPurchaseRequest) {
        val response: HttpResponse = ktorClient.post(VERIFY_URL) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        val stringBody: String = response.body()
        println(stringBody)
        ktorClient.close()
    }
}