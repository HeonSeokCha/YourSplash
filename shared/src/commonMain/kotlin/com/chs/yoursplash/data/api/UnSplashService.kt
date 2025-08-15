package com.chs.yoursplash.data.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class UnSplashService (
    val service: HttpClient
) {
    suspend inline fun <reified T>requestUnsplash(
        url: String,
        params: HashMap<String, String> = hashMapOf(),
    ): T {
        return try {
            service.get(url) {
                params.forEach { (key, value) ->
                    this.parameter(key, value)
                }
            }.body<T>()
        } catch (e: Exception) {
            throw e
        }
    }
}