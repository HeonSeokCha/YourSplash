package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.ShimmerImage
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhotoDetailScreenRoot(
    viewModel: PhotoDetailViewModel,
    onNavigate: (BrowseInfo) -> Unit,
    onClose: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                PhotoDetailEffect.Close -> onClose()
                is PhotoDetailEffect.NavigatePhotoDetail -> onNavigate(BrowseInfo.Photo(effect.id))
                is PhotoDetailEffect.NavigatePhotoTag -> onNavigate(BrowseInfo.PhotoTag(effect.tag))
                is PhotoDetailEffect.NavigateUserDetail -> onNavigate(BrowseInfo.User(effect.name))
                is PhotoDetailEffect.ShowToast -> Unit
            }
        }
    }

    PhotoDetailScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
}


@Composable
fun PhotoDetailScreen(
    state: PhotoDetailState,
    onIntent: (PhotoDetailIntent) -> Unit
) {
    val scrollState = rememberScrollState()
    val lazyVerticalStaggeredState = rememberLazyStaggeredGridState()

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            Column {
                ShimmerImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .shimmer(state.isDetailLoading && (state.imageDetailInfo == null)),
                    url = state.imageDetailInfo?.urls
                )

                ItemUserInfoFromPhotoDetail(state.imageDetailInfo?.user) {
                    onIntent(PhotoDetailIntent.ClickUser(it))
                }

                HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

                if (state.imageDetailInfo != null) {
                    ImageDetailInfo(state.imageDetailInfo) { selectTag ->
                        onIntent(PhotoDetailIntent.ClickTag(selectTag))
                    }
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
}

@Composable
private fun ItemUserInfoFromPhotoDetail(
    info: User?,
    onUser: (String) -> Unit
) {
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
                    if (info?.userName == null) return@clickable
                    onUser(info.userName)
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShimmerImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(100)),
                url = info?.photoProfile?.large
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier
                    .shimmer(visible = info == null),
                text = info?.name ?: Constants.TEXT_PREVIEW,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}