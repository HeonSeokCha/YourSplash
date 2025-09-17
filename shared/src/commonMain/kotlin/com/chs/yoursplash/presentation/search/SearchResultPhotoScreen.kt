package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_result
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchResultPhotoScreen(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit
) {
    val pagingList = state.searchPhotoList?.collectAsLazyPagingItems()

    val isEmpty by remember {
        derivedStateOf {
            pagingList != null
                    && pagingList.loadState.refresh is LoadState.NotLoading
                    && pagingList.loadState.append.endOfPaginationReached
                    && pagingList.itemCount == 0
        }
    }

    if (pagingList != null) {
        LaunchedEffect(pagingList.loadState.refresh) {
            when (pagingList.loadState.refresh) {
                is LoadState.Loading -> {
                    onIntent(SearchIntent.Photo.Loading)
                }

                is LoadState.Error -> {
                    (pagingList.loadState.refresh as LoadState.Error).error.run {
                        onIntent(SearchIntent.Photo.OnError(this.message))
                    }
                }

                is LoadState.NotLoading -> onIntent(SearchIntent.Photo.LoadComplete)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            when {
                state.photoLoadingState -> {
                    items(Constants.COUNT_LOADING_ITEM) {
                        ImageCard(photoInfo = null, onPhotoClick = {}, onUserClick = {})
                    }
                }

                isEmpty -> {
                    item {
                        ItemEmpty(
                            modifier = Modifier.fillParentMaxSize(),
                            text = stringResource(Res.string.text_no_result)
                        )
                    }
                }

                else -> {
                    items(count = pagingList.itemCount) { idx ->
                        val item = pagingList[idx]
                        ImageCard(
                            photoInfo = item,
                            onPhotoClick = {
                                onIntent(SearchIntent.ClickBrowseInfo(BrowseInfo.Photo(it)))
                            },
                            onUserClick = {
                                onIntent(SearchIntent.ClickBrowseInfo(BrowseInfo.User(it)))
                            }
                        )
                    }
                }
            }
        }
    }
}