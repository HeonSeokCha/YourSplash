package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.BrowseInfo.*
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.presentation.browse.BrowseScreens
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun CollectionDetailScreenRoot(
    viewModel: CollectionDetailViewModel,
    onNavigate: (BrowseScreens) -> Unit,
    onClose: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingItems.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CollectionDetailEffect.NavigatePhotoDetail -> {
                    onNavigate(BrowseScreens.PhotoDetailScreen(effect.id))
                }
                is CollectionDetailEffect.NavigateUserDetail -> {
                    onNavigate(BrowseScreens.UserDetailScreen(effect.name))
                }
                is CollectionDetailEffect.ShowToast -> Unit
                CollectionDetailEffect.Close -> onClose()
            }
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> viewModel.changeIntent(CollectionDetailIntent.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    viewModel.changeIntent(CollectionDetailIntent.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> viewModel.changeIntent(CollectionDetailIntent.LoadComplete)
        }
    }

    CollectionDetailScreen(
        state = state,
        pagingItems = pagingItems,
        onIntent = viewModel::changeIntent
    )
}

@Composable
fun CollectionDetailScreen(
    state: CollectionDetailState,
    pagingItems: LazyPagingItems<Photo>,
    onIntent: (CollectionDetailIntent) -> Unit
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
        header = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(visible = state.isDetailLoad),
                text = if (state.collectionDetailInfo == null) {
                    Constants.TEXT_PREVIEW
                } else {
                    "${state.collectionDetailInfo.totalPhotos} Photos ● " + "Create by ${state.collectionDetailInfo.user.name}"
                },
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        isShowTopBar = true,
        onCloseClick = { onIntent(CollectionDetailIntent.ClickClose) }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            when {
                state.isPagingLoading -> {
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
                        ImageCard(
                            photoInfo = pagingItems[idx],
                            onPhotoClick = { onIntent(CollectionDetailIntent.ClickPhoto(it)) },
                            onUserClick = { onIntent(CollectionDetailIntent.ClickUser(it)) }
                        )
                    }
                }
            }
        }
    }
}