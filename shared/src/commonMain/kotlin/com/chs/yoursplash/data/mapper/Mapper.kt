package com.chs.yoursplash.data.mapper

import com.chs.yoursplash.data.model.*
import com.chs.yoursplash.domain.model.Exif
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.model.PhotoLocation
import com.chs.yoursplash.domain.model.PhotoPosition
import com.chs.yoursplash.domain.model.PhotoUrls
import com.chs.yoursplash.domain.model.RelatedCollectionPreview
import com.chs.yoursplash.domain.model.RelatedPhotoCollection
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UnSplashTag
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.domain.model.UserPhotos
import com.chs.yoursplash.domain.model.UserProfileImage

fun ResponsePhoto.toUnSplashImage(quality: LoadQuality): Photo {
    return Photo(
        id = id,
        color = color,
        blurHash = blurHash,
        width = width,
        height = height,
        urls = urls.toUnSplashImageUrls(quality: LoadQuality),
        user = user.toUnSplashUser(quality: LoadQuality)
    )
}

fun ResponsePhotoUrls.toUnSplashImageUrls(quality: LoadQuality): String {
    return when (quality) {
        LoadQuality.Raw -> this.raw
        LoadQuality.Full -> this.full
        LoadQuality.Regular -> this.regular
        LoadQuality.Small -> this.small
        LoadQuality.Thumb -> this.thumb
    }
}

fun ResponseUser.toUnSplashUser(quality: LoadQuality): User {
    return User(
        id = id,
        userName = userName,
        name = name,
        photoProfile = photoProfile.toUnsplashUserProfileImage(),
        photos = photos.map {
            it.toUserPhotos(quality)
        }
    )
}

fun ResponseUserPhotos.toUserPhotos(quality: LoadQuality): UserPhotos {
    return UserPhotos(
        id = id,
        blurHash = blurHash,
        urls = urls.toUnSplashImageUrls(quality)
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
        totalPhotos = totalPhotos
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

fun ResponsePhotoDetail.toUnSplashImageDetail(quality: LoadQuality): PhotoDetail {
    return PhotoDetail(
        id = id,
        width = width,
        height = height,
        color = color,
        blurHash = blurHash,
        likes = likes,
        urls = urls.toUnSplashImageUrls(quality),
        description = description,
        user = user.toUnSplashUser(quality),
        exif = exif.toUnSplashExif(),
        location = location.toUnSplashLocation(),
        tags = tags.map { it.toUnSplashTag() },
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

//fun ResponseRelatedPhotoCollection.toRelatedPhotoCollection(): RelatedPhotoCollection {
//    return RelatedPhotoCollection(
//        total = total,
//        result = results.map { it.toPhotoCollection(quality) }
//    )
//}

fun ResponseRelatedCollectionPreview.toRelatedCollectionPreview(quality: LoadQuality): RelatedCollectionPreview {
    return RelatedCollectionPreview(
        id = id,
        urls = urls.toUnSplashImageUrls(quality),
        blurHash = blurHash
    )
}

fun ResponseCollection.toPhotoCollection(quality: LoadQuality): UnSplashCollection {
    return UnSplashCollection(
        id = id,
        title = title,
        totalPhotos = totalPhotos,
        user = user.toUnSplashUser(quality),
        previewPhotos = previewPhotos?.map {
            it.toRelatedCollectionPreview(quality)
        }
    )
}