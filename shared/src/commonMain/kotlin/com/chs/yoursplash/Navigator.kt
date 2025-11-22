package com.chs.yoursplash

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class Navigator(startDest: Any) {
    val backStack : SnapshotStateList<Any> = mutableStateListOf(startDest)

    fun navigateTo(destination: Any){
        backStack.add(destination)
    }

    fun goBack(){
        backStack.removeLastOrNull()
    }

    fun onBrowse() {

    }
}