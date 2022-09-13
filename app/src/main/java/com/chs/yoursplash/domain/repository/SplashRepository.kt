package com.chs.yoursplash.domain.repository

interface SplashRepository {

    suspend fun getSplashImages()

    suspend fun getSplashImageDetail(id: String)

    suspend fun getSearchResultImages(query: String)

}