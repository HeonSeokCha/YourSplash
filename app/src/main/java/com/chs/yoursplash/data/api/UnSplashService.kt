package com.chs.yoursplash.data.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

class UnSplashService @Inject constructor(
    val service: HttpClient
) {
    suspend inline fun <reified T>requestUnsplash(
        url: String,
        params: HashMap<String, String> = hashMapOf(),
    ): T {
        val a = service.get(url) {
            params.forEach { (key, value) ->
                this.parameter(key, value)
            }
        }

        val b = a.bodyAsText()

        return a.body<T>()
    }
}