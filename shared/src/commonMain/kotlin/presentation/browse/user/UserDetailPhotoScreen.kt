package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import presentation.base.ImageCard
import util.Constants

@Composable
fun UserDetailPhotoScreen(
    photoList: LazyPagingItems<Photo>?,
    loadQuality: String,
    onNavigate: (Screens) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
    ) {
        if (photoList != null) {
            items(
                count = photoList.itemCount,
                key = photoList.itemKey { it.id }
            ) { idx ->
                AsyncImage(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        ).clickable {
                            if (photoList[idx] != null) {
                                onNavigate(Screens.ImageDetailScreen(photoList[idx]!!.id))
                            }
                        },
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(Constants.getPhotoQualityUrl(photoList[idx]?.urls, loadQuality))
                        .crossfade(true)
                        .build(),
                    placeholder = Constants.getPlaceHolder(photoList[idx]?.blurHash),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
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