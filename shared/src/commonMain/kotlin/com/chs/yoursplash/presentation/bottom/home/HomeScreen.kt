package com.chs.yoursplash.presentation.bottom.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemPullToRefreshBox

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,
    onBrowse: (BrowseInfo) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigatePhotoDetail -> onBrowse(BrowseInfo.Photo(effect.id))
                is HomeEffect.NavigateUserDetail -> onBrowse(BrowseInfo.User(effect.name))
                is HomeEffect.ShowToast -> Unit
            }
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> viewModel.handleIntent(HomeIntent.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    viewModel.handleIntent(HomeIntent.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> viewModel.handleIntent(HomeIntent.LoadComplete)
        }
    }

    HomeScreen(
        state = state,
        pagingItems = pagingItems,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    pagingItems: LazyPagingItems<Photo>,
    onIntent: (HomeIntent) -> Unit
) {
    ItemPullToRefreshBox(
        isRefreshing = state.isRefresh,
        onRefresh = {
            onIntent(HomeIntent.RefreshData)
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
            items(
                count = pagingItems.itemCount,
            ) { idx ->
                val photo = pagingItems[idx]
                ImageCard(
                    photoInfo = photo,
                    onPhotoClick = {
                        onIntent(HomeIntent.ClickPhoto(it))
                    },
                    onUserClick = {
                        onIntent(HomeIntent.ClickUser(it))
                    }
                )
            }
        }
    }
}