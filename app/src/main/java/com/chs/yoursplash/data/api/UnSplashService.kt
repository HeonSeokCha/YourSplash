package com.chs.yoursplash.data.api

import com.chs.yoursplash.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.util.Objects
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnSplashService @Inject constructor(
    val service: HttpClient
) {
    suspend inline fun <reified T>requestUnsplash(
        url: String,
        params: HashMap<String, String> = hashMapOf(),
    ): T {
        return service.get("${Constants.UNSPLAH_BASE_URL}$url") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            params.forEach { (key, value) ->
                this.parameter(key, value)
            }
        }.body()
    }
}