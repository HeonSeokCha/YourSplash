package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.CollectionSimpleCard
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun UserDetailCollectionScreen(
    collectionList: LazyPagingItems<UnSplashCollection>?,
    loadQuality: String,
    onNavigate: (Screens) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (collectionList != null) {
            items(
                count = collectionList.itemCount,
                key = collectionList.itemKey { it.id }
            ) { idx ->
                CollectionSimpleCard(
                    collectionInfo = collectionList[idx],
                    loadQuality = loadQuality
                ) {
                    onNavigate(Screens.CollectionDetailScreen(it))
                }
            }

            when (collectionList.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = (collectionList.loadState.refresh as LoadState.Error).error.message
                                ?: "Unknown Error.."
                        )
                    }
                }

                else -> Unit
            }

            when (collectionList.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = (collectionList.loadState.refresh as LoadState.Error).error.message
                                ?: "Unknown Error.."
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}