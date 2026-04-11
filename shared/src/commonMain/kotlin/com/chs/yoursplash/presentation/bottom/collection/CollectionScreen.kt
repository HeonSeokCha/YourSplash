package com.chs.yoursplash.presentation.bottom.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
    val snackBarHost = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CollectionEffect.NavigateCollectionDetail -> onBrowse(BrowseInfo.Collection(effect.id))
                is CollectionEffect.NavigateUserDetail -> onBrowse(BrowseInfo.User(effect.name))
                is CollectionEffect.ShowSnackBar -> {
                    snackBarHost.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    CollectionScreen(
        state = state,
        pagingItems = pagingItems,
        snackBarHost = snackBarHost,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun CollectionScreen(
    state: CollectionState,
    pagingItems: LazyPagingItems<UnSplashCollection>,
    snackBarHost: SnackbarHostState,
    onIntent: (CollectionIntent) -> Unit
) {
    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> onIntent(CollectionIntent.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    onIntent(CollectionIntent.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> onIntent(CollectionIntent.LoadComplete)
        }
    }


    val isEmpty by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.NotLoading
                    && pagingItems.loadState.append.endOfPaginationReached
                    && pagingItems.itemCount == 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ItemPullToRefreshBox(
            isRefreshing = state.isRefresh,
            onRefresh = {
                onIntent(CollectionIntent.RefreshData)
                pagingItems.refresh()
            }
        ) {
            if (state.isGrid) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    when {
                        state.isLoading -> {
                            items(Constants.COUNT_LOADING_ITEM) {
                                CollectionInfoCard(null, isShowUserInfo = false)
                            }
                        }

                        isEmpty -> {
                            item(span = StaggeredGridItemSpan.FullLine) {
                                ItemEmpty(
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
                                    isShowUserInfo = false,
                                    onCollection = { onIntent(CollectionIntent.ClickCollection(it)) },
                                    onUser = { onIntent(CollectionIntent.ClickUser(it)) }
                                )
                            }
                        }
                    }
                }
            } else {
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
                                CollectionInfoCard(null, isShowUserInfo = false)
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


        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            hostState = snackBarHost
        )
    }
}