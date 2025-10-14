package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_result
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.presentation.base.CollectionInfoCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.util.Constants
import io.ktor.websocket.Frame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchResultCollectionScreen(
    state: SearchState,
    pagingList: Flow<PagingData<UnSplashCollection>>,
    onIntent: (SearchIntent) -> Unit
) {
    val pagingItems = pagingList.collectAsLazyPagingItems()
    val lazyColScrollState = rememberLazyListState()
    val isEmpty by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.NotLoading
                    && pagingItems.loadState.append.endOfPaginationReached
                    && pagingItems.itemCount == 0
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                onIntent(SearchIntent.Collection.Loading)
                lazyColScrollState.animateScrollToItem(0)
            }

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    onIntent(SearchIntent.Collection.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> onIntent(SearchIntent.Collection.LoadComplete)
        }
    }

    LaunchedEffect(pagingItems.loadState.append) {
        when (pagingItems.loadState.append) {
            is LoadState.Loading -> {
                onIntent(SearchIntent.Collection.AppendLoading)
            }

            is LoadState.Error -> {
                (pagingItems.loadState.append as LoadState.Error).error.run {
                }
            }

            is LoadState.NotLoading -> onIntent(SearchIntent.Collection.AppendLoadComplete)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        when {
            state.isCollectionLoading -> {
                items(Constants.COUNT_LOADING_ITEM) {
                    CollectionInfoCard(collectionInfo = null)
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
                items(count = pagingItems.itemCount) { idx ->
                    val item = pagingItems[idx]
                    CollectionInfoCard(
                        collectionInfo = item,
                        onCollection = {
                            onIntent(SearchIntent.ClickBrowseInfo(BrowseInfo.Collection(it)))
                        },
                        onUser = {
                            onIntent(SearchIntent.ClickBrowseInfo(BrowseInfo.User(it)))
                        }
                    )
                }

                if (state.isCollectAppendLoading) {
                    items(Constants.COUNT_LOADING_ITEM) {
                        CollectionInfoCard(collectionInfo = null)
                    }
                }
            }
        }
    }
}
