package com.chs.yoursplash.presentation.bottom.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.base.CollectionInfoCard


@Composable
fun CollectionScreenRoot(
    viewModel: CollectionViewModel,
    onBrowse: (BrowseInfo) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectionScreen(state) { event ->
        when (event) {
            is CollectionEvent.BrowseCollectionDetail -> onBrowse(BrowseInfo.Collection(event.id))
            is CollectionEvent.BrowseUserDetail -> onBrowse(BrowseInfo.User(event.name))
        }
    }
}

@Composable
fun CollectionScreen(
    state: CollectionState,
    onEvent: (CollectionEvent) -> Unit
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
                    onCollection = { onEvent(CollectionEvent.BrowseCollectionDetail(it)) },
                    onUser = { onEvent(CollectionEvent.BrowseUserDetail(it)) }
                )
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