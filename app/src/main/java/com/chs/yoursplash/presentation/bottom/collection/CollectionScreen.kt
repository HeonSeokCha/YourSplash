package com.chs.yoursplash.presentation.bottom.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.presentation.base.CollectionInfoCard

@Composable
fun CollectionScreen(
    state: CollectionState,
    onClick: (Pair<String, String>) -> Unit
) {
    val lazyPagingItems = state.collectionList?.collectAsLazyPagingItems()

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
                key = lazyPagingItems.itemKey { it.id }
            ) { idx ->
                val collectionInfo = lazyPagingItems[idx]
                CollectionInfoCard(
                    collectionInfo = collectionInfo,
                    loadQuality = state.loadQuality
                ) {
                    onClick(it)
                }
            }

            when (lazyPagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        CollectionInfoCard(collectionInfo = null)
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
                        CollectionInfoCard(collectionInfo = null)
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