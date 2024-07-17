package com.chs.yoursplash.data.api

import android.util.Log
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
        return try {
            service.get(url) {
                params.forEach { (key, value) ->
                    this.parameter(key, value)
                }
            }.body<T>()
        } catch (e: Exception) {
            Log.e("CHS_LOG", e.message.toString())
            throw e
        }
    }
}