package com.chs.yoursplash.data

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class FileManager(
    private val context: Context
) {
    actual suspend fun saveFile(
        fileName: String,
        data: ByteArray
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            saveImage(byteArray = data, fileName = fileName)
        }
    }

    actual suspend fun isFileExist(fileName: String): Result<Boolean> {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )
        val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)
        return withContext(Dispatchers.IO) {
            return@withContext try {
                context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    null
                ).use { cursor ->
                    return@use Result.success(
                        cursor != null && cursor.count != 0
                    )
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun saveImage(
        byteArray: ByteArray,
        fileName: String
    ): Result<Unit> {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "${Environment.DIRECTORY_PICTURES}/YourSplash"
            )
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (imageUri == null) return Result.failure(Exception("imageUri is Null."))

        return try {
            resolver.openOutputStream(imageUri)?.use { outputStream ->
                outputStream.write(byteArray)
            }

            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(imageUri, contentValues, null, null)

            Result.success(Unit)
        } catch (e: Exception) {
            resolver.delete(imageUri, null, null)
            Result.failure(e)
        }
    }
}