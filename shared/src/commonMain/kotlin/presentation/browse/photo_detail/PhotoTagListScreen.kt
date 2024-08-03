package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import presentation.base.ImageCard

@Composable
fun PhotoTagListScreen(
    state: PhotoTagListState,
    onClick: (Pair<String, String>) -> Unit
) {
    val resultPagingItems = state.tagSearchResultList?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        if (resultPagingItems != null) {
            items(
                count = resultPagingItems.itemCount,
                key = resultPagingItems.itemKey { it.id }
            ) { idx ->
                ImageCard(
                    photoInfo = resultPagingItems[idx],
                    loadQuality = state.loadQuality
                ) {
                    onClick(it)
                }
            }

            when (resultPagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = (resultPagingItems.loadState.refresh as LoadState.Error).error.message
                                ?: "Unknown Error.."
                        )
                    }
                }

                else -> Unit
            }

            when (resultPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = (resultPagingItems.loadState.refresh as LoadState.Error).error.message
                                ?: "Unknown Error.."
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}