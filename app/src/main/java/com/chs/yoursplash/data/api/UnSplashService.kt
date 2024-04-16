package com.chs.yoursplash.data.api

import com.chs.yoursplash.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class UnSplashService @Inject constructor(
    val service: HttpClient
) {
    suspend inline fun <reified T>requestUnsplash(
        url: String,
        params: HashMap<String, String> = hashMapOf(),
    ): T {
        return service.get(url) {
            params.forEach { (key, value) ->
                this.parameter(key, value)
            }
        }.body<T>()
    }
}