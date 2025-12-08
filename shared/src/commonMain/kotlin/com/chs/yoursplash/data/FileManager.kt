package com.chs.yoursplash.data

expect class FileManager {
    suspend fun saveFile(fileName: String, data: ByteArray): Result<Unit>
    suspend fun isFileExist(fileName: String): Boolean
}