package com.chs.yoursplash.data.mapper

import com.chs.yoursplash.data.model.ResponseUnSplashImage
import com.chs.yoursplash.data.model.ResponseUnSplashImageUrls
import com.chs.yoursplash.data.model.ResponseUnSplashUser
import com.chs.yoursplash.data.model.ResponseUnsplashUserProfileImage
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.domain.model.UnSplashImageUrls
import com.chs.yoursplash.domain.model.UnSplashUser
import com.chs.yoursplash.domain.model.UnsplashUserProfileImage

fun ResponseUnSplashImage.toUnSplashImage(): UnSplashImage {
    return UnSplashImage(
        id = id,
        color = color,
        width = width,
        height = height,
        urls = urls.toUnSplashImageUrls(),
        user = user.toUnSplashUser()
    )
}

fun ResponseUnSplashImageUrls.toUnSplashImageUrls(): UnSplashImageUrls {
    return UnSplashImageUrls(
        raw = raw,
        full = full,
        small = small,
        thumb = thumb,
        small_s3 = small_s3
    )
}


fun ResponseUnSplashUser.toUnSplashUser(): UnSplashUser {
    return UnSplashUser(
        id = id,
        userName = userName,
        name = name,
        photoProfile = photoProfile.toUnsplashUserProfileImage()
    )
}

fun ResponseUnsplashUserProfileImage.toUnsplashUserProfileImage(): UnsplashUserProfileImage {
    return UnsplashUserProfileImage(
        small = small,
        medium = medium,
        large = large
    )
}