package com.chs.yoursplash.domain.model

data class Photo(
    val id: String,
    val color: String,
    val blurHash: String?,
    val width: Int,
    val height: Int,
    val urls: String?,
    val user: User
)