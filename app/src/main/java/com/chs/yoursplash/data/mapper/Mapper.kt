package com.chs.yoursplash.data.mapper

import com.chs.yoursplash.data.model.*
import com.chs.yoursplash.domain.model.*

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

fun ResponseUnSplashExif.toUnSplashExif(): UnSplashExif {
    return UnSplashExif(
        make = make,
        model = model,
        name = name,
        exposureTime = exposureTime,
        aperture = aperture,
        focalLength = focalLength,
        iso = iso
    )
}

fun ResponseUnSplashLocation.toUnSplashLocation(): UnSplashLocation {
    return UnSplashLocation(
        title = title,
        name = name,
        city = city,
        country = country,
        position = position.toUnSplashPosition()
    )
}

fun ResponseUnSplashPosition.toUnSplashPosition(): UnSplashPosition {
    return UnSplashPosition(
        latitude = latitude,
        longitude = longitude
    )
}

fun ResponseUnSplashImageDetail.toUnSplashImageDetail(): UnSplashImageDetail {
    return UnSplashImageDetail(
        id = id,
        width = width,
        height = height,
        color = color,
        urls = urls.toUnSplashImageUrls(),
        description = description,
        user = user.toUnSplashUser(),
        exif = exif.toUnSplashExif(),
        location = location.toUnSplashLocation()
    )
}