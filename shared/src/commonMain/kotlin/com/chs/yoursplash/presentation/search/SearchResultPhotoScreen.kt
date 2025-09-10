package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.base.ImageCard
import kotlinx.coroutines.launch

@Composable
fun SearchResultPhotoScreen(
    state: SearchState,
    onBrowse: (BrowseInfo) -> Unit
) {
    val pagingList = state.searchPhotoList?.collectAsLazyPagingItems()
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        if (pagingList != null) {
            items(count = pagingList.itemCount) { idx ->
                val item = pagingList[idx]
                ImageCard(
                    photoInfo = item,
                    onPhotoClick = { onBrowse(BrowseInfo.Photo(it)) },
                    onUserClick = { onBrowse(BrowseInfo.User(it)) }
                )
            }

            when (pagingList.loadState.refresh) {
                is LoadState.Loading -> {
                    coroutineScope.launch {
                        scrollState.scrollToItem(0)
                    }
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> Unit
            }

            when (pagingList.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}