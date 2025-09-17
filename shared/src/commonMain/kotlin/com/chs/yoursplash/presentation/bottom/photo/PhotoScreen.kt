package com.chs.yoursplash.presentation.bottom.photo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.ItemPullToRefreshBox
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhotoScreenRoot(
    viewModel: PhotoViewModel,
    onBrowse: (BrowseInfo) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PhotoEffect.NavigatePhotoDetail -> onBrowse(BrowseInfo.Photo(effect.id))
                is PhotoEffect.NavigateUserDetail -> onBrowse(BrowseInfo.User(effect.name))
                is PhotoEffect.ShowToast -> Unit
            }
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> viewModel.handleIntent(PhotoIntent.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    viewModel.handleIntent(PhotoIntent.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> viewModel.handleIntent(PhotoIntent.LoadComplete)
        }
    }

    PhotoScreen(
        state = state,
        pagingItems = pagingItems,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun PhotoScreen(
    state: PhotoState,
    pagingItems: LazyPagingItems<Photo>,
    onIntent: (PhotoIntent) -> Unit
) {
    val isEmpty by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.NotLoading
                    && pagingItems.loadState.append.endOfPaginationReached
                    && pagingItems.itemCount == 0
        }
    }

    ItemPullToRefreshBox(
        isRefreshing = state.isRefresh,
        onRefresh = {
            onIntent(PhotoIntent.RefreshData)
            pagingItems.refresh()
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            when {
                state.isLoading -> {
                    items(Constants.COUNT_LOADING_ITEM) {
                        ImageCard(null)
                    }
                }

                isEmpty -> {
                    item {
                        ItemEmpty(
                            modifier = Modifier.fillParentMaxSize(),
                            text = stringResource(Res.string.text_no_photos)
                        )
                    }
                }

                else -> {
                    items(count = pagingItems.itemCount) { idx ->
                        val photo = pagingItems[idx]
                        ImageCard(
                            photoInfo = photo,
                            onPhotoClick = {
                                onIntent(PhotoIntent.ClickPhoto(it))
                            },
                            onUserClick = {
                                onIntent(PhotoIntent.ClickUser(it))
                            }
                        )
                    }
                }
            }

        }
    }
}