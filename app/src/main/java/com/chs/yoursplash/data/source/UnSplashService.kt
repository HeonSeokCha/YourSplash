package com.chs.yoursplash.data.source

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

    suspend fun getPhotos(): List<ResponsePhoto> {
        return service.get("${Constants.UNSPLAH_URL}/photos") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getPhotoDetail(id: String): ResponsePhotoDetail {
        return service.get("${Constants.UNSPLAH_URL}/photos/$id") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getPhotoRelated(id: String): ResponseRelatedPhoto {
        return service.get("${Constants.UNSPLAH_URL}/photos/$id/related") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getCollection(): List<ResponseCollection> {
        return service.get("${Constants.UNSPLAH_URL}/collections") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getCollectionDetail(id: String): ResponseCollection {
        return service.get("${Constants.UNSPLAH_URL}/collections/$id") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getUserDetail(userName: String): ResponseUserDetail {
        return service.get("${Constants.UNSPLAH_URL}/users/$userName") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }


    suspend fun getUserPhotos(userName: String): List<ResponsePhoto> {
        return service.get("${Constants.UNSPLAH_URL}/users/$userName/photos") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("per_page", 20)
        }.body()
    }

    suspend fun getUserLikes(userName: String): List<ResponsePhoto> {
        return service.get("${Constants.UNSPLAH_URL}/users/$userName/likes") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getUserCollections(userName: String): List<ResponseCollection> {
        return service.get("${Constants.UNSPLAH_URL}/users/$userName/collections") {
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