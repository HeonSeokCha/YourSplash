package com.chs.yoursplash.domain.model

data class PhotoLocation(
    val title: String? = null,
    val name: String? = null,
    val city: String? = null,
    val country: String? = null,
    val position: PhotoPosition
)
