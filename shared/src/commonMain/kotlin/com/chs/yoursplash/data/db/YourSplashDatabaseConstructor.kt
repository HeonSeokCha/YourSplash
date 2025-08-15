package com.chs.yoursplash.data.db

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object YourSplashDatabaseConstructor : RoomDatabaseConstructor<YourSplashDatabase> {
    override fun initialize(): YourSplashDatabase
}