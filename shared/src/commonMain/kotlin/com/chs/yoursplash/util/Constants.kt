package com.chs.yoursplash.util

import com.chs.yoursplash.BuildKonfig
import com.chs.yoursplash.domain.model.Orientations
import com.chs.yoursplash.domain.model.PhotoUrls
import com.chs.yoursplash.domain.model.SortType

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
    val PHOTO_SOURCE_URL: ((String) -> String) = { "https://unsplash.com/photos/$it" }
    val COLLECTION_SOURCE_URL: ((String) -> String) = { "https://unsplash.com/collections/$it" }
    val CLIENT_ID: String = BuildKonfig.API_ACCESS_KEY
    const val PAGING_SIZE: Int = 3
    const val GET_SEARCH_PHOTOS: String = "/search/photos"
    const val GET_SEARCH_COLLECTIONS: String = "/search/collections"
    const val GET_SEARCH_USERS: String = "/search/users"
    val TITLE_LOAD_QUALITY: Pair<String, String> = "Load Quality" to PREFERENCE_KEY_LOAD_QUALITY
    val TITLE_DOWNLOAD_QUALITY: Pair<String, String> = "Download Quality" to PREFERENCE_KEY_DOWNLOAD_QUALITY
    val TITLE_WALLPAPER_LOAD_QUALITY: Pair<String, String> = "Wallpaper Quality" to PREFERENCE_KEY_WALLPAPER_QUALITY

    const val PREFERENCE_KEY_LOAD_QUALITY: String = "load_quality"
    const val PREFERENCE_KEY_DOWNLOAD_QUALITY: String = "download_quality"
    const val PREFERENCE_KEY_WALLPAPER_QUALITY: String = "wallpaper_quality"
    const val TARGET_ID: String = "target_id"
    const val TARGET_TYPE: String = "target_type"
    const val TARGET_PHOTO: String = "target_photo"
    const val TARGET_COLLECTION: String = "target_collection"
    const val TARGET_USER: String = "target_user"
    const val ARG_KEY_PHOTO_ID: String = "photoId"
    const val ARG_KEY_COLLECTION_ID: String = "collectionId"
    const val ARG_KEY_USER_NAME: String = "userName"
    const val ARG_KEY_TAG_NAME: String = "tagName"
    const val TEXT_PREVIEW: String = "This is Title."

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

    val SORT_BY_LIST = SortType.entries.map { it.name to it.rawValue }

    val SEARCH_ORI_LIST = Orientations.entries.map { it.name to it.rawValue }

    const val COUNT_LOADING_ITEM: Int = 20
}