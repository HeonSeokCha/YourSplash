package com.chs.yoursplash.presentation.bottom.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemPullToRefreshBox
import com.chs.yoursplash.presentation.bottom.collection.CollectionEvent
import kotlinx.coroutines.launch

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,
    onBrowse: (BrowseInfo) -> Unit
) {
   val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is HomeEvent.BrowseUserDetail -> onBrowse(BrowseInfo.User(event.name))

                is HomeEvent.BrowsePhotoDetail -> onBrowse(BrowseInfo.Photo(event.id))

                else -> viewModel.changeEvent(event)
            }
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    val lazyPagingItems = state.pagingImageList?.collectAsLazyPagingItems()

    val coroutineScope = rememberCoroutineScope()

    ItemPullToRefreshBox(
        isRefreshing = state.isRefresh,
        onRefresh = {
            coroutineScope.launch {
                onEvent(HomeEvent.OnRefresh)
            }
        }
    ) {
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
                        onPhotoClick = {
                            onEvent(HomeEvent.BrowsePhotoDetail(it))
                        },
                        onUserClick = {
                            onEvent(HomeEvent.BrowseUserDetail(it))
                        }
                    )
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
    }
}