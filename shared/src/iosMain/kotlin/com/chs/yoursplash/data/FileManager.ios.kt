package com.chs.yoursplash.data

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSArray
import platform.Foundation.NSData
import platform.Foundation.NSPredicate
import platform.Foundation.arrayWithObject
import platform.Foundation.arrayWithObjects
import platform.Foundation.create
import platform.Photos.PHAsset
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHAssetCollection
import platform.Photos.PHAssetCollectionChangeRequest
import platform.Photos.PHAssetCollectionSubtypeAny
import platform.Photos.PHAssetCollectionTypeAlbum
import platform.Photos.PHAssetResource
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHFetchOptions
import platform.Photos.PHObjectPlaceholder
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy
import kotlin.coroutines.suspendCoroutine

actual class FileManager {
    actual suspend fun saveFile(fileName: String, data: ByteArray): Result<Boolean> {
        TODO("Not yet implemented")
    }

    actual suspend fun isFileExist(fileName: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalForeignApi::class)
    fun UIImage.toByteArray(): ByteArray? {
        val imageData = UIImagePNGRepresentation(this) ?: return null
        return ByteArray(imageData.length.toInt()).apply {
            usePinned { pinned ->
                memcpy(pinned.addressOf(0), imageData.bytes, imageData.length)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    fun ByteArray.toUIImage(): UIImage? {
        val data = this.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = this.size.toULong())
        }
        return UIImage.imageWithData(data)
    }
}