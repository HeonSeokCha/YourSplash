package com.chs.yoursplash.data

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

actual class FileManager(
    private val context: Context
) {
    companion object {
        private val PATH: String = Environment.getExternalStorageDirectory().absolutePath +
                "/${Environment.DIRECTORY_PICTURES}/YourSplash"
    }

    actual suspend fun saveFile(
        fileName: String,
        data: ByteArray
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            saveImage(byteArray = data, fileName = fileName)
        }
    }

    actual suspend fun isFileExist(fileName: String): Result<Boolean> = withContext(Dispatchers.IO) {
        val dir: File = File(PATH)

        return@withContext withContext(Dispatchers.IO) {
            if (!dir.exists()) return@withContext Result.success(false)

            Result.success(dir.listFiles()?.find { it.name == fileName } != null)
        }
    }

    private fun saveImage(
        byteArray: ByteArray,
        fileName: String
    ): Result<Unit> {
        val dir: File = File(PATH)
        if (!dir.exists()) dir.mkdirs()

        val file: File = File(dir, fileName)

        return try {
            FileOutputStream(file).use {
                it.write(byteArray)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}