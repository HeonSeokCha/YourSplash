package com.chs.yoursplash.data.mapper

import com.chs.yoursplash.data.db.entity.PhotoSaveEntity
import com.chs.yoursplash.data.model.*
import com.chs.yoursplash.domain.model.*

fun ResponsePhoto.toUnSplashImage(): Photo {
    return Photo(
        id = id,
        color = color,
        blurHash = blurHash,
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
        regular = regular,
        small = small,
        thumb = thumb
    )
}

fun ResponseUser.toUnSplashUser(): User {
    return User(
        id = id,
        userName = userName,
        name = name,
        photoProfile = photoProfile.toUnsplashUserProfileImage(),
        photos = photos.map {
            it.toUserPhotos()
        }
    )
}

fun ResponseUserPhotos.toUserPhotos(): UserPhotos {
    return UserPhotos(
        id = id,
        blurHash = blurHash,
        urls = urls.toUnSplashImageUrls()
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
        blurHash = blurHash,
        likes = likes,
        urls = urls.toUnSplashImageUrls(),
        description = description,
        user = user.toUnSplashUser(),
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

fun ResponseRelatedPhotoCollection.toRelatedPhotoCollection(): RelatedPhotoCollection {
    return RelatedPhotoCollection(
        total = total,
        result = results.map { it.toPhotoCollection() }
    )
}

fun ResponseRelatedCollectionPreview.toRelatedCollectionPreview(): RelatedCollectionPreview {
    return RelatedCollectionPreview(
        id = id,
        urls = urls.toUnSplashImageUrls(),
        blurHash = blurHash
    )
}

fun ResponseCollection.toPhotoCollection(): UnSplashCollection {
    return UnSplashCollection(
        id = id,
        title = title,
        totalPhotos = totalPhotos,
        unSplashTags = tags.map { it.toUnSplashTag() },
        user = user.toUnSplashUser(),
        previewPhotos = previewPhotos?.map {
            it.toRelatedCollectionPreview()
        }
    )
}

fun PhotoSaveInfo.toPhotoSaveEntity(): PhotoSaveEntity {
    return PhotoSaveEntity(
        photoId = this.id,
        blurHash = this.blurHash,
        photoUrl = this.url,
    )
}

fun PhotoSaveEntity.toPhotoSaveInfo(): PhotoSaveInfo {
    return PhotoSaveInfo(
        id = this.photoId,
        blurHash = this.blurHash,
        url = this.photoUrl
    )
}