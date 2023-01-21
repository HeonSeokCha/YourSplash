package com.chs.yoursplash.util

import com.chs.yoursplash.BuildConfig
import com.chs.yoursplash.domain.model.PhotoUrls

object Constants {
    const val UNSPLAH_BASE_URL: String = "https://api.unsplash.com"
    const val GET_PHOTOS: String = "/photos"
    val GET_PHOTO_DETAIL: ((String) -> String) = {"/photos/$it"}
    val GET_PHOTO_RELATED: ((String) -> String) = {"/photos/$it/related"}
    const val GET_COLLECTION: String = "/collections"
    val GET_COLLECTION_DETAILED: ((String) -> String) = {"/collections/$it"}
    val GET_COLLECTION_PHOTOS: ((String) -> String) = {"/collections/$it/photos"}
    val GET_COLLECTION_RELATED: ((String) -> String) = {"/collections/$it/related"}
    val GET_USER_DETAILED: ((String) -> String) = {"/users/$it"}
    val GET_USER_PHOTOS: ((String) -> String) = {"/users/$it/photos"}
    val GET_USER_LIKES: ((String) -> String) = {"/users/$it/likes"}
    val GET_USER_COLLECTIONS: ((String) -> String) = {"/users/$it/collections"}
    const val GET_SEARCH_PHOTOS: String = "/search/photos"
    const val GET_SEARCH_COLLECTIONS: String = "/search/collections"
    const val GET_SEARCH_USERS: String = "/search/users"


    const val CLIENT_ID: String = BuildConfig.API_ACCESS_KEY
    const val PREFERENCE_NAME: String = "setting_preferences"
    const val PREFERENCE_KEY_LOAD_QUALITY: String ="load_quality"
    const val PREFERENCE_KEY_DOWNLOAD_QUALITY: String ="download_quality"
    const val PREFERENCE_KEY_WALLPAPER_QUALITY: String ="wallpaper_quality"
    const val TARGET_ID: String = "target_id"
    const val TARGET_TYPE: String = "target_type"
    const val TARGET_PHOTO: String = "target_photo"
    const val TARGET_COLLECTION: String = "target_collection"
    const val TARGET_USER: String = "target_user"
    const val SEARCH_PHOTO: String = "search_photo"
    const val SEARCH_COLLECTION: String = "search_collection"
    const val SEARCH_USER: String = "search_user"

    val SORT_BY_LIST = hashMapOf(
        Pair("RELEVANCE", "relevant"),
        Pair("LATEST", "latest")
    )

    val SEARCH_COLOR_LIST = hashMapOf(
        Pair("Any", null),
        Pair("Black And White", "black_and_white"),
        Pair("Black", "black"),
        Pair("white", "white"),
        Pair("Yellow", "yellow"),
        Pair("Orange", "orange"),
        Pair("Red", "red"),
        Pair("Purple", "purple"),
        Pair("Magenta", "magenta"),
        Pair("Green", "green"),
        Pair("Teal", "teal")
    )

    val SEARCH_ORI_LIST = hashMapOf(
        Pair("Any", null),
        Pair("Landscape", "landscape"),
        Pair("Portrait", "portrait"),
        Pair("Squarish", "squarish"),
    )

    val QUALITY_LIST = linkedMapOf(
        Pair("Raw", "raw"),
        Pair("Full", "full"),
        Pair("Regular", "regular"),
        Pair("Small", "small"),
        Pair("Thumb", "thumb"),
    )

    fun getPhotoQualityUrl(
        urlInfo: PhotoUrls?,
        quality: String
    ): String? {
        return when(quality) {
            "raw" -> {
                urlInfo?.raw
            }
            "full" -> {
                urlInfo?.full
            }
            "regular" -> {
                urlInfo?.regular
            }
            "small" -> {
                urlInfo?.small
            }
            "thumb" -> {
                urlInfo?.thumb
            }
            else -> null
        }
    }
}
