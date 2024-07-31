package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.presentation.base.UserCard
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot

@Composable
fun SearchResultUserScreen(
    state: SearchState,
    clickable: (Pair<String, String>) -> Unit
) {
    val pagingList = state.searchUserList?.collectAsLazyPagingItems()
    val scrollState = rememberLazyListState()

    LaunchedEffect(state.searchQuery) {
        if (state.searchQuery != null) {
            snapshotFlow { state.searchQuery }
                .distinctUntilChanged()
                .filterNot { it.isNotEmpty() }
                .collect {
                    scrollState.scrollToItem(0)
                }
        }
    }

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
                UserCard(
                    userInfo = item,
                    loadQuality = state.loadQuality,
                    userClickAble = { userName ->
                        clickable(Constants.TARGET_USER to userName)
                    }, photoClickAble = { photoId ->
                        clickable(Constants.TARGET_PHOTO to photoId)
                    }
                )
            }

            when (pagingList.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        UserCard(
                            userInfo = null,
                            userClickAble = {},
                            photoClickAble = {}
                        )
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
                        UserCard(
                            userInfo = null,
                            userClickAble = {},
                            photoClickAble = {}
                        )
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
