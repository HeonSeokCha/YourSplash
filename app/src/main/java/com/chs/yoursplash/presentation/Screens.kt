package com.chs.yoursplash.presentation

sealed class Screens(
    val route: String
) {
    object ImageDetailScreen : Screens("imageDetailScreen")
    object UserDetailScreen : Screens("userDetailScreen")
    object SearchScreen : Screens("searchScreen")

}