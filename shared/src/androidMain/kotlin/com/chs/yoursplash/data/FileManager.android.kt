package com.chs.yoursplash.data

import android.content.Context
import android.os.Environment
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

    actual suspend fun isFileExist(fileName: String): Boolean = withContext(Dispatchers.IO) {
        val dir: File = File(PATH)

        return@withContext withContext(Dispatchers.IO) {
            if (!dir.exists() && dir.listFiles() == null) return@withContext false

            val result = dir.listFiles()!!.any { it.nameWithoutExtension == fileName }

            result
        }
    }

    private fun saveImage(
        byteArray: ByteArray,
        fileName: String
    ): Result<Unit> {
        val dir: File = File(PATH)
        if (!dir.exists()) dir.mkdirs()

        val file: File = File(dir, "${fileName}.png")

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