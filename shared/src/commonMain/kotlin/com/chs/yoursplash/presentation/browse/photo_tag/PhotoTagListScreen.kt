package com.chs.yoursplash.presentation.browse.photo_tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.paging.compose.itemKey
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.browse.BrowseScreens
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhotoTagListScreenRoot(
    viewModel: PhotoTagListViewModel,
    onClose: () -> Unit,
    onNavigate: (BrowseScreens) -> Unit
) {
    val pagingItems = viewModel.pagingItems.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                PhotoTagEffect.Close -> onClose()
                is PhotoTagEffect.NavigatePhotoDetail -> {
                    onNavigate(BrowseScreens.PhotoDetailScreen(effect.id))
                }
                is PhotoTagEffect.NavigateUser -> {
                    onNavigate(BrowseScreens.UserDetailScreen(effect.name))
                }
                is PhotoTagEffect.ShowToast -> Unit
            }
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> viewModel.handleIntent(PhotoTagIntent.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    viewModel.handleIntent(PhotoTagIntent.OnError(this.message ?: ""))
                }
            }

            is LoadState.NotLoading -> viewModel.handleIntent(PhotoTagIntent.LoadComplete)
        }
    }

    PhotoTagListScreen(
        state = state,
        pagingItems = pagingItems,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun PhotoTagListScreen(
    state: PhotoTagListState,
    pagingItems: LazyPagingItems<Photo>,
    onIntent: (PhotoTagIntent) -> Unit
) {
    val scrollState = rememberScrollState()

    val isEmpty by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.NotLoading
                    && pagingItems.loadState.append.endOfPaginationReached
                    && pagingItems.itemCount == 0
        }
    }

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        isShowTopBar = true,
        header = { },
        onCloseClick = { onIntent(PhotoTagIntent.ClickClose) }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
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
                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey { it.id }
                    ) { idx ->
                        ImageCard(
                            photoInfo = pagingItems[idx],
                            onPhotoClick = { onIntent(PhotoTagIntent.ClickPhoto(it)) },
                            onUserClick = { onIntent(PhotoTagIntent.ClickUser(it)) }
                        )
                    }
                }
            }
        }
    }
}