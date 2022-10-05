package com.chs.yoursplash.data.mapper

import com.chs.yoursplash.data.model.*
import com.chs.yoursplash.domain.model.*

fun ResponsePhoto.toUnSplashImage(): Photo {
    return Photo(
        id = id,
        color = color,
        width = width,
        height = height,
        urls = urls.toUnSplashImageUrls(),
        user = user.toUnSplashUser()
    )
}

fun ResponsePhotoUrls.toUnSplashImageUrls(): PhotoUrls {
    return PhotoUrls(
        raw = raw,
        full = full,
        small = small,
        thumb = thumb,
        small_s3 = small_s3
    )
}

fun ResponseUser.toUnSplashUser(): User {
    return User(
        id = id,
        userName = userName,
        name = name,
        photoProfile = photoProfile.toUnsplashUserProfileImage()
    )
}

fun ResponseUserDetail.toUserDetail(): UserDetail {
    return UserDetail(
        id = id,
        userName = userName,
        name = name,
        bio = bio,
        location = location,
        profileImage = profileImage.toUnsplashUserProfileImage(),
        totalCollections = totalCollection,
        totalLikes = totalLikes,
        totalPhotos = totalPhotos,
        tags = tags.custom.map { it.toUnSplashTag() },
        followersCount = followersCount
    )
}

fun ResponseUserProfileImage.toUnsplashUserProfileImage(): UserProfileImage {
    return UserProfileImage(
        small = small,
        medium = medium,
        large = large
    )
}

fun ResponseExif.toUnSplashExif(): Exif {
    return Exif(
        make = make,
        model = model,
        name = name,
        exposureTime = exposureTime,
        aperture = aperture,
        focalLength = focalLength,
        iso = iso
    )
}

fun ResponsePhotoLocation.toUnSplashLocation(): PhotoLocation {
    return PhotoLocation(
        title = title,
        name = name,
        city = city,
        country = country,
        position = position.toUnSplashPosition()
    )
}

fun ResponsePhotoPosition.toUnSplashPosition(): PhotoPosition {
    return PhotoPosition(
        latitude = latitude,
        longitude = longitude
    )
}

fun ResponsePhotoDetail.toUnSplashImageDetail(): PhotoDetail {
    return PhotoDetail(
        id = id,
        width = width,
        height = height,
        color = color,
        likes = likes,
        urls = urls.toUnSplashImageUrls(),
        description = description,
        user = user.toUnSplashUser(),
        exif = exif.toUnSplashExif(),
        location = location.toUnSplashLocation(),
        tags = tags.map { it.toUnSplashTag() },
        relatedCollection = relatedCollections.toRelatedPhotoCollection(),
        views = views,
        downloads = downloads
    )
}

fun ResponseUnSplashTag.toUnSplashTag(): UnSplashTag {
    return UnSplashTag(
        title = title,
        type = type
    )
}

fun ResponseRelatedPhotoCollection.toRelatedPhotoCollection(): RelatedPhotoCollection {
    return RelatedPhotoCollection(
        total = total,
        result = results.map { it.toPhotoCollection() }
    )
}

fun ResponseRelatedCollectionPreview.toRelatedCollectionPreview(): RelatedCollectionPreview {
    return RelatedCollectionPreview(
        id = id,
        urls = urls.toUnSplashImageUrls()
    )
}

fun ResponseCollection.toPhotoCollection(): UnSplashCollection {
    return UnSplashCollection(
        id = id,
        title = title,
        unSplashTags = tags.map { it.toUnSplashTag() },
        user = user.toUnSplashUser(),
        previewPhotos = previewPhotos.map {
            it.toRelatedCollectionPreview()
        }
    )
}