package com.chs.yoursplash.data.db

import androidx.room3.RoomDatabaseConstructor

expect object YourSplashDatabaseConstructor : RoomDatabaseConstructor<YourSplashDatabase> {
    override fun initialize(): YourSplashDatabase
}