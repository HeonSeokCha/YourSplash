package com.chs.yoursplash.data.source

import android.util.Log
import com.chs.yoursplash.data.model.ResponseUnSplashImage
import com.chs.yoursplash.data.model.ResponseUnSplashImageDetail
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.get

@Singleton
class UnSplashService @Inject constructor(
    private val service: HttpClient
) {

    suspend fun getSplashImage(): List<ResponseUnSplashImage> {
        return service.get("${Constants.UNSPLAH_URL}/photos") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getImageDetail(id: String): ResponseUnSplashImageDetail {
        return service.get("${Constants.UNSPLAH_URL}/photos/$id") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getSearchResultImage(
        query: String,
        page: Int,
        orderBy: String = "relevant",
        color: String? = null,
        orientation: String? = null
    ) {
        service.get("${Constants.UNSPLAH_URL}/search/photos") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("query", query)
            this.parameter("page", page)
            if (orientation != null) {
                this.parameter("orientation", orientation)
            }
            if (color != null) {
                this.parameter("color", color)
            }
        }
    }

    suspend fun getSearchResultCollection(
        query: String,
        page: Int,
    ) {
        service.get("${Constants.UNSPLAH_URL}/search/collections") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("query", query)
            this.parameter("page", page)
        }
    }
}