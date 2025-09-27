package com.chs.yoursplash.data

expect class FileManager {
    suspend fun saveFile(fileName: String, data: ByteArray): Result<Boolean>
    suspend fun isFileExist(fileName: String): Result<Boolean>
}