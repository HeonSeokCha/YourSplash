package com.chs.yoursplash.domain.repository

interface SplashRepository {

    suspend fun getSplashImages()

    suspend fun getSplashImageDetail()

    suspend fun getSearchResultImages(query: String)

}