package com.chs.yoursplash.presentation.bottom.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_collections
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.presentation.base.CollectionInfoCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.ItemPullToRefreshBox
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource


@Composable
fun CollectionScreenRoot(
    viewModel: CollectionViewModel,
    onBrowse: (BrowseInfo) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CollectionEffect.NavigateCollectionDetail -> onBrowse(BrowseInfo.Photo(effect.id))
                is CollectionEffect.NavigateUserDetail -> onBrowse(BrowseInfo.User(effect.name))
                is CollectionEffect.ShowToast -> Unit
            }
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> viewModel.handleIntent(CollectionIntent.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    viewModel.handleIntent(CollectionIntent.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> viewModel.handleIntent(CollectionIntent.LoadComplete)
        }
    }

    CollectionScreen(
        state = state,
        pagingItems = pagingItems,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun CollectionScreen(
    state: CollectionState,
    pagingItems: LazyPagingItems<UnSplashCollection>,
    onIntent: (CollectionIntent) -> Unit
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
            onIntent(CollectionIntent.RefreshData)
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
                        CollectionInfoCard(null)
                    }
                }

                isEmpty -> {
                    item {
                        ItemEmpty(
                            modifier = Modifier.fillParentMaxSize(),
                            text = stringResource(Res.string.text_no_collections)
                        )
                    }
                }

                else -> {
                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey { it.id }
                    ) { idx ->
                        val collectionInfo = pagingItems[idx]
                        CollectionInfoCard(
                            collectionInfo = collectionInfo,
                            onCollection = { onIntent(CollectionIntent.ClickCollection(it)) },
                            onUser = { onIntent(CollectionIntent.ClickUser(it)) }
                        )
                    }
                }
            }

        }
    }
}