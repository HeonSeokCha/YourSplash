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
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_photos
import com.chs.youranimelist.res.text_no_result
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.UserCard
import com.chs.yoursplash.presentation.bottom.photo.PhotoIntent
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchResultUserScreen(
    state: SearchState,
    pagingList: Flow<PagingData<User>>,
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
                onIntent(SearchIntent.User.Loading)
                lazyColScrollState.animateScrollToItem(0)
            }

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    onIntent(SearchIntent.User.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> onIntent(SearchIntent.User.LoadComplete)
        }
    }

    LaunchedEffect(pagingItems.loadState.append) {
        when (pagingItems.loadState.append) {
            is LoadState.Loading -> {
                onIntent(SearchIntent.User.AppendLoading)
            }

            is LoadState.Error -> {
                (pagingItems.loadState.append as LoadState.Error).error.run {
                    onIntent(SearchIntent.User.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> onIntent(SearchIntent.User.AppendLoadComplete)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        when {
            state.isUserLoading -> {
                items(Constants.COUNT_LOADING_ITEM) {
                    UserCard(userInfo = null, userClickAble = {}, photoClickAble = {})
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
                    UserCard(
                        userInfo = item,
                        userClickAble = { userName ->
                            onIntent(SearchIntent.ClickBrowseInfo(BrowseInfo.User(userName)))
                        }, photoClickAble = { photoId ->
                            onIntent(SearchIntent.ClickBrowseInfo(BrowseInfo.Photo(photoId)))
                        }
                    )
                }

                if (state.isUserAppendLoading) {
                    items(Constants.COUNT_LOADING_ITEM) {
                        UserCard(userInfo = null, userClickAble = {}, photoClickAble = {})
                    }
                }
            }
        }
    }
}
