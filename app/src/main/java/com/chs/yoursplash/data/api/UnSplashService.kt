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

    suspend fun getPhotos(page: Int): List<ResponsePhoto> {
        return service.get("${Constants.UNSPLAH_URL}/photos") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("page", page)
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

    suspend fun getCollection(page: Int): List<ResponseCollection> {
        return service.get("${Constants.UNSPLAH_URL}/collections") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("page", page)
        }.body()
    }

    suspend fun getCollectionDetail(id: String): ResponseCollection {
        return service.get("${Constants.UNSPLAH_URL}/collections/$id") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }.body()
    }

    suspend fun getCollectionPhotos(
        id: String,
        page: Int
    ): List<ResponsePhoto> {
        return service.get("${Constants.UNSPLAH_URL}/collections/$id/photos") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("page", page)
        }.body()
    }

    suspend fun getCollectionRelated(id: String): List<ResponseCollection> {
        return service.get("${Constants.UNSPLAH_URL}/collections/$id/related") {
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


    suspend fun getUserPhotos(
        userName: String,
        page: Int
    ): List<ResponsePhoto> {
        return service.get("${Constants.UNSPLAH_URL}/users/$userName/photos") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("page", page)
        }.body()
    }

    suspend fun getUserLikes(
        userName: String,
        page: Int
    ): List<ResponsePhoto> {
        return service.get("${Constants.UNSPLAH_URL}/users/$userName/likes") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("page", page)
        }.body()
    }

    suspend fun getUserCollections(
        userName: String,
        page: Int
    ): List<ResponseCollection> {
        return service.get("${Constants.UNSPLAH_URL}/users/$userName/collections") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("page", page)
        }.body()
    }

    suspend fun getSearchResultPhoto(
        query: String,
        page: Int,
        orderBy: String,
        color: String?,
        orientation: String?
    ): ResponseSearchPhotos {
        return service.get("${Constants.UNSPLAH_URL}/search/photos") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("query", query)
            this.parameter("page", page)
            this.parameter("order_by", orderBy)
            if (color != null) this.parameter("color", color)
            if (orientation != null) this.parameter("orientation", orientation)
        }.body()
    }

    suspend fun getSearchResultCollection(
        query: String,
        page: Int,
    ): ResponseSearchCollections {
        return service.get("${Constants.UNSPLAH_URL}/search/collections") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("query", query)
            this.parameter("page", page)
        }.body()
    }

    suspend fun getSearchResultUser(
        query: String,
        page: Int,
    ): ResponseSearchUsers {
        return service.get("${Constants.UNSPLAH_URL}/search/users") {
            this.headers.append("Accept-Version", "v1")
            this.headers.append("Authorization", "Client-ID ${Constants.CLIENT_ID}")
            this.parameter("query", query)
            this.parameter("page", page)
        }.body()
    }
}