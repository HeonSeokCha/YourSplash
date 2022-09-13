package com.chs.yoursplash.domain.model

data class UnSplashLocation(
    val title: String? = null,
    val name: String? = null,
    val city: String? = null,
    val country: String? = null,
    val position: UnSplashPosition
)

data class UnSplashPosition(
    val latitude: Float? = null,
    val longitude: Float? = null
)
