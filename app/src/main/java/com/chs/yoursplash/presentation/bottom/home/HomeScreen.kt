package com.chs.yoursplash.presentation.bottom.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.util.Constants


@Composable
fun HomeScreen(
    state: HomeState,
    onUserClick: (String) -> Unit,
    onPhotoClick: (String) -> Unit
) {
    val lazyPagingItems = state.pagingImageList?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        if (lazyPagingItems != null) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey(key = { it.id }),
            ) { idx ->
                val photo = lazyPagingItems[idx]
                ImageCard(
                    photoInfo = photo,
                    loadQuality = state.loadQuality,
                    userClickAble = { userName ->
                        onUserClick(userName)
                    }, photoClickAble = { photoId ->
                        onPhotoClick(photoId)
                    }
                )
            }


            when (lazyPagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(
                            photoInfo = null,
                            userClickAble = {},
                            photoClickAble = {}
                        )
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = (lazyPagingItems.loadState.refresh as LoadState.Error).error.message
                                ?: "Unknown Error.."
                        )
                    }
                }

                else -> Unit
            }

            when (lazyPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(
                            photoInfo = null,
                            userClickAble = {},
                            photoClickAble = {}
                        )
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = (lazyPagingItems.loadState.refresh as LoadState.Error).error.message
                                ?: "Unknown Error.."
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}