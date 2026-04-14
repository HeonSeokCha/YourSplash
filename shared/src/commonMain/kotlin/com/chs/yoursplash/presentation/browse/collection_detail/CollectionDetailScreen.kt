package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_collections
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.BrowseInfo.*
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.GradientTopBar
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.presentation.bottom.photo.PhotoIntent
import com.chs.yoursplash.presentation.browse.BrowseScreens
import com.chs.yoursplash.presentation.browse.BrowseScreens.*
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailIntent
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun CollectionDetailScreenRoot(
    viewModel: CollectionDetailViewModel,
    onNavigate: (BrowseScreens) -> Unit,
    onBrowser: (String) -> Unit,
    onClose: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingItems.collectAsLazyPagingItems()
    val snackBarHost = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CollectionDetailEffect.NavigatePhotoDetail -> {
                    onNavigate(PhotoDetailScreen(effect.id))
                }

                is CollectionDetailEffect.NavigateUserDetail -> {
                    onNavigate(UserDetailScreen(effect.name))
                }

                is CollectionDetailEffect.ShowSnackBar -> {
                    snackBarHost.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
                CollectionDetailEffect.Close -> onClose()
                is CollectionDetailEffect.NavigateBrowser -> onBrowser(effect.id)
            }
        }
    }

    CollectionDetailScreen(
        state = state,
        pagingItems = pagingItems,
        snackBarHost = snackBarHost,
        onIntent = viewModel::changeIntent
    )
}

@Composable
fun CollectionDetailScreen(
    state: CollectionDetailState,
    pagingItems: LazyPagingItems<Photo>,
    snackBarHost: SnackbarHostState,
    onIntent: (CollectionDetailIntent) -> Unit
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> onIntent(CollectionDetailIntent.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    onIntent(CollectionDetailIntent.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> onIntent(CollectionDetailIntent.LoadComplete)
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
        CollapsingToolbarScaffold(
            scrollState = scrollState,
            isShowTopBar = true,
            expandContent = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
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
            collapsedContent = {
                GradientTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary),
                    topBarIcon = Icons.Default.OpenInBrowser,
                    onIconClick = {
                        if (state.collectionDetailInfo == null) return@GradientTopBar
                        onIntent(CollectionDetailIntent.ClickOpenBrowser(state.collectionDetailInfo.id))
                    },
                    onCloseClick = { onIntent(CollectionDetailIntent.ClickClose) }
                )
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
                        state.isPagingLoading -> {
                            items(Constants.COUNT_LOADING_ITEM) {
                                ImageCard(null, isShowUserInfo = false)
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
                            items(count = pagingItems.itemCount) { idx ->
                                ImageCard(
                                    photoInfo = pagingItems[idx],
                                    isShowUserInfo = false,
                                    onPhotoClick = { onIntent(CollectionDetailIntent.ClickPhoto(it)) },
                                    onUserClick = { onIntent(CollectionDetailIntent.ClickUser(it)) }
                                )
                            }
                        }
                    }
                }
            } else {
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

        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            hostState = snackBarHost
        )
    }
}