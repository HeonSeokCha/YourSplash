package com.chs.yoursplash.util

import com.chs.yoursplash.BuildConfig

object Constants {
    const val UNSPLAH_URL: String = "https://api.unsplash.com"
    const val CLIENT_ID: String = BuildConfig.API_ACCESS_KEY
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
}
