package com.chs.yoursplash.data

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.Foundation.create
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy
import kotlin.coroutines.resumeWithException

actual class FileManager {
    actual suspend fun saveFile(fileName: String, data: ByteArray): Result<Unit> {

        return suspendCancellableCoroutine { continuation ->
            val nsData = data.toNSData()
            val image = UIImage.imageWithData(nsData)
                ?: run {
                    continuation.resumeWithException(Exception("Failed to create UIImage"))
                    return@suspendCancellableCoroutine
                }

            PHPhotoLibrary.requestAuthorization { status ->
                if (status != PHAuthorizationStatusAuthorized) {
                    return@requestAuthorization
                }

                saveToPhotoLibrary(
                    image = image,
                    fileName = fileName,
                    continuation = continuation
                )
            }
        }
    }

    private fun saveToPhotoLibrary(
        image: UIImage,
        fileName: String,
        continuation: CancellableContinuation<Result<Unit>>
    ) {
        var assetId: String? = null

        PHPhotoLibrary.sharedPhotoLibrary().performChanges(
            changeBlock = {
                val request = PHAssetChangeRequest.creationRequestForAssetFromImage(image)
                assetId = request.placeholderForCreatedAsset?.localIdentifier
            },
            completionHandler = { success, error ->
                if (!success && assetId == null) {
                    continuation.resumeWithException(
                        Exception("Failed to save image: ${error?.localizedDescription}")
                    )
                }

                continuation.resume(Result.success(Unit)) { cause, _, _ -> }
            }
        )
    }

    actual suspend fun isFileExist(fileName: String): Boolean {
        return false
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun NSData.toByteArray(): ByteArray {
        return ByteArray(this.length.toInt()).apply {
            usePinned {
                memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ByteArray.toNSData(): NSData {
        return this.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = this.size.toULong())
        }
    }
}