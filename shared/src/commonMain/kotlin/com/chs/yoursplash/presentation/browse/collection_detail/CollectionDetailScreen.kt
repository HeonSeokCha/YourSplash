package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.util.Constants

@Composable
fun CollectionDetailScreen(
    state: CollectionDetailState,
    onClick: (Pair<String, String>) -> Unit
) {
    val lazyPagingItems = state.collectionPhotos?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        if (state.isError) {
            item {
                Text(state.errorMessage ?: "UnknownError")
            }
        } else {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(visible = state.collectionDetailInfo == null),
                    text = if (state.collectionDetailInfo == null) {
                        Constants.TEXT_PREVIEW
                    } else {
                        "${state.collectionDetailInfo.totalPhotos} Photos ● " +
                                "Create by ${state.collectionDetailInfo.user.name}"
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (lazyPagingItems != null) {
            items(
                count = lazyPagingItems.itemCount,
            ) { idx ->

                ImageCard(
                    photoInfo = lazyPagingItems[idx],
                    loadQuality = state.loadQuality
                ) {
                    onClick(it)
                }
            }

            when (lazyPagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
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
                        ImageCard(photoInfo = null)
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

    when (lazyPagingItems?.loadState?.source?.refresh) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
        }

        else -> {}
    }
}