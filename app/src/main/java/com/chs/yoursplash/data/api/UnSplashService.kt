package com.chs.yoursplash.data.api

import android.util.Log
import com.chs.yoursplash.data.model.*
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnSplashService @Inject constructor(
    private val service: HttpClient
) {
    suspend fun requestUnsplash(
        url: String,
        params: HashMap<String, String>,
    ): List<ResponsePhoto> {
        return service.get("${Constants.UNSPLAH_BASE_URL}$url") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            params.forEach { key, value ->
                this.parameter(key, value)
            }
        }.body()
    }
}