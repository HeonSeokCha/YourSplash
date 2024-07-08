package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun UserDetailLikeScreen(
    photoList: LazyPagingItems<Photo>?,
    loadQuality: String,
    onNavigate: (Screens) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (photoList != null && photoList.itemCount != 0) {
            items(photoList.itemCount) { idx ->

                ImageCard(
                    photoInfo = photoList[idx],
                    loadQuality = loadQuality,
                    photoClickAble = {
                        if (photoList[idx] != null) {
                            onNavigate(
                                Screens.ImageDetailScreen(photoList[idx]!!.id)
                            )
                        }
                    }, userClickAble = {
                        if (photoList[idx] != null) {
                            onNavigate(
                                Screens.UserDetailScreen(photoList[idx]!!.user.userName)
                            )
                        }
                    }
                )
            }
        }
    }
}