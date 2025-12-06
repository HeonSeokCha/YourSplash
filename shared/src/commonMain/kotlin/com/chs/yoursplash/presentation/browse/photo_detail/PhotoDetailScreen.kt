package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.presentation.browse.BrowseScreens
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.ShimmerImage
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.presentation.browse.BrowseScreens.*
import com.chs.yoursplash.presentation.toSettingUrl
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhotoDetailScreenRoot(
    viewModel: PhotoDetailViewModel,
    onNavigate: (BrowseScreens) -> Unit,
    onClose: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHost = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                PhotoDetailEffect.Close -> onClose()
                is PhotoDetailEffect.NavigatePhotoDetail -> {
                    onNavigate(PhotoDetailScreen(effect.id))
                }

                is PhotoDetailEffect.NavigatePhotoTag -> {
                    onNavigate(PhotoTagResultScreen(effect.tag))
                }

                is PhotoDetailEffect.NavigateUserDetail -> {
                    onNavigate(UserDetailScreen(effect.name))
                }

                is PhotoDetailEffect.NavigatePhotoDetailView -> {
                    onNavigate(BrowseScreens.PhotoDetailViewScreen(effect.url))
                }

                is PhotoDetailEffect.ShowToast -> Unit
                PhotoDetailEffect.DownloadFailed -> {
                    snackBarHost.showSnackbar(
                        message = "Download Failed.",
                        withDismissAction = true
                    )
                }
                PhotoDetailEffect.DownloadSuccess -> {
                    snackBarHost.showSnackbar(
                        message = "Download Complete.",
                        withDismissAction = true
                    )
                }
            }
        }
    }

    PhotoDetailScreen(
        state = state,
        snackBarHost = snackBarHost,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun PhotoDetailScreen(
    state: PhotoDetailState,
    snackBarHost: SnackbarHostState,
    onIntent: (PhotoDetailIntent) -> Unit
) {
    val scrollState = rememberScrollState()
    val lazyVerticalStaggeredState = rememberLazyStaggeredGridState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CollapsingToolbarScaffold(
            scrollState = scrollState,
            header = {
                Column {
                    ShimmerImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .shimmer(state.isDetailLoading && (state.imageDetailInfo == null))
                            .clickable {
                                if (state.imageDetailInfo == null) return@clickable
                                onIntent(
                                    PhotoDetailIntent.ClickPhotoDetail(state.imageDetailInfo.url)
                                )
                            },
                        url = state.imageDetailInfo?.url
                    )

                    ItemUserInfoFromPhotoDetail(
                        state = state,
                        onUser = { onIntent(PhotoDetailIntent.ClickUser(it)) },
                        onDownload = { onIntent(PhotoDetailIntent.ClickDownload(it)) }
                    )

                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

                    ImageDetailInfo(state.imageDetailInfo) { selectTag ->
                        onIntent(PhotoDetailIntent.ClickTag(selectTag))
                    }
                }
            },
            isShowTopBar = false,
            onCloseClick = { onIntent(PhotoDetailIntent.ClickClose) }
        ) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize(),
                state = lazyVerticalStaggeredState,
                columns = StaggeredGridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp,
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 32.dp)
            ) {
                when {
                    state.isRelatedLoading -> {
                        items(Constants.COUNT_LOADING_ITEM) {
                            Box(
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(280.dp)
                                    .shimmer(true)
                            )
                        }
                    }

                    state.imageRelatedList.isEmpty() -> {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            ItemEmpty(
                                modifier = Modifier.fillMaxSize(),
                                text = stringResource(Res.string.text_no_photos)
                            )
                        }
                    }

                    else -> {
                        items(count = state.imageRelatedList.size) { idx ->
                            val item = state.imageRelatedList[idx]
                            ShimmerImage(
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(280.dp)
                                    .clickable { onIntent(PhotoDetailIntent.ClickPhoto(item.id)) },
                                url = item.urls
                            )
                        }
                    }
                }
            }
        }
        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp),
            hostState = snackBarHost
        )
    }
}

@Composable
private fun ItemUserInfoFromPhotoDetail(
    state: PhotoDetailState,
    onUser: (String) -> Unit,
    onDownload: (String) -> Unit
) {
    val info = state.imageDetailInfo
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    if (info?.user?.userName == null) return@clickable
                    onUser(info.user.userName)
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShimmerImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(100)),
                url = info?.user?.photoProfile?.large
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier
                    .shimmer(visible = info == null),
                text = info?.user?.name ?: Constants.TEXT_PREVIEW,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        IconButton(
            onClick = {
                if (info == null || state.isFileDownLoading) return@IconButton

                onDownload(info.downloadUrl)
            }
        ) {
            if (state.isFileDownLoading) {
                Icon(imageVector = Icons.Default.Downloading, contentDescription = null)
            } else {
                Icon(imageVector = Icons.Default.Download, contentDescription = null)
            }
        }
    }
}