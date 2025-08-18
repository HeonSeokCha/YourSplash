package com.chs.yoursplash

import com.chs.yoursplash.domain.model.PhotoUrls

object Constants {
    const val UNSPLAH_BASE_URL: String = "api.unsplash.com"
    const val GET_PHOTOS: String = "/photos"
    val GET_PHOTO_DETAIL: ((String) -> String) = { "/photos/$it" }
    val GET_PHOTO_RELATED: ((String) -> String) = { "/photos/$it/related" }
    const val GET_COLLECTION: String = "/collections"
    val GET_COLLECTION_DETAILED: ((String) -> String) = { "/collections/$it" }
    val GET_COLLECTION_PHOTOS: ((String) -> String) = { "/collections/$it/photos" }
    val GET_COLLECTION_RELATED: ((String) -> String) = { "/collections/$it/related" }
    val GET_USER_DETAILED: ((String) -> String) = { "/users/$it" }
    val GET_USER_PHOTOS: ((String) -> String) = { "/users/$it/photos" }
    val GET_USER_LIKES: ((String) -> String) = { "/users/$it/likes" }
    val GET_USER_COLLECTIONS: ((String) -> String) = { "/users/$it/collections" }
    const val GET_SEARCH_PHOTOS: String = "/search/photos"
    const val GET_SEARCH_COLLECTIONS: String = "/search/collections"
    const val GET_SEARCH_USERS: String = "/search/users"


    val CLIENT_ID: String = BuildKonfig.API_ACCESS_KEY
    const val PAGING_SIZE: Int = 3

    val SORT_BY_LIST = listOf(
        Pair("RELEVANCE", "relevant"),
        Pair("LATEST", "latest")
    )

    val SEARCH_COLOR_LIST = listOf(
        Pair("Any", null),
        Pair("Black And White", "black_and_white"),
        Pair("Black", "black"),
        Pair("White", "white"),
        Pair("Yellow", "yellow"),
        Pair("Orange", "orange"),
        Pair("Red", "red"),
        Pair("Purple", "purple"),
        Pair("Magenta", "magenta"),
        Pair("Green", "green"),
        Pair("Teal", "teal")
    )

    val SEARCH_ORI_LIST = listOf(
        Pair("Any", null),
        Pair("Landscape", "landscape"),
        Pair("Portrait", "portrait"),
        Pair("Squarish", "squarish"),
    )

    val QUALITY_LIST: List<String> = listOf(
        "Raw",
        "Full",
        "Regular",
        "Small",
        "Thumb"
    )

    fun getPhotoQualityUrl(
        urlInfo: PhotoUrls?,
        quality: String
    ): String? {
        return when (quality) {
            "Raw" -> {
                urlInfo?.raw
            }

            "Full" -> {
                urlInfo?.full
            }

            "Regular" -> {
                urlInfo?.regular
            }

            "Small" -> {
                urlInfo?.small
            }

            "Thumb" -> {
                urlInfo?.thumb
            }

            else -> null
        }
    }
}