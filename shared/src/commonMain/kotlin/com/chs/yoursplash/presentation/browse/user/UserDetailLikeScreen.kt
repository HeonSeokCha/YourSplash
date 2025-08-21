package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.util.Constants

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
        if (photoList != null) {
            items(
                count = photoList.itemCount,
                key = photoList.itemKey { it.id }
            ) { idx ->

                ImageCard(
                    photoInfo = photoList[idx],
                    loadQuality = loadQuality,
                    onPhotoClick = {},
                    onUserClick = {}
                )
            }

            when (photoList.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = (photoList.loadState.refresh as LoadState.Error).error.message
                                ?: "Unknown Error.."
                        )
                    }
                }

                else -> Unit
            }

            when (photoList.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = (photoList.loadState.refresh as LoadState.Error).error.message
                                ?: "Unknown Error.."
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}