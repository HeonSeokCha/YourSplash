package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_items
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.presentation.browse.BrowseScreens
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.ShimmerImage
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.presentation.toCommaFormat
import com.chs.yoursplash.presentation.ui.theme.Purple200
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserDetailScreenRoot(
    viewModel: UserDetailViewModel,
    onClose: () -> Unit,
    onNavigate: (BrowseScreens) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val photoPaging = viewModel.photoPaging
    val likePaging = viewModel.likePaging
    val collectPaging = viewModel.collectPaging

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                UserDetailEffect.Close -> onClose()
                is UserDetailEffect.NavigateCollectionDetail -> onNavigate(BrowseScreens.CollectionDetailScreen(effect.id))
                is UserDetailEffect.NavigatePhotoDetail -> onNavigate(BrowseScreens.PhotoDetailScreen(effect.id))
                is UserDetailEffect.NavigateUserDetail -> onNavigate(BrowseScreens.UserDetailScreen(effect.name))
                is UserDetailEffect.ShowToast -> Unit
            }
        }
    }

    UserDetailScreen(
        state = state,
        photoPaging = photoPaging,
        likePaging = likePaging,
        collectPaging = collectPaging,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    state: UserDetailState,
    photoPaging: Flow<PagingData<Photo>>,
    likePaging: Flow<PagingData<Photo>>,
    collectPaging: Flow<PagingData<UnSplashCollection>>,
    onIntent: (UserDetailIntent) -> Unit
) {
    val pagerState = rememberPagerState { state.tabList.size }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(state.selectIdx) {
        pagerState.animateScrollToPage(state.selectIdx)
    }

    LaunchedEffect(pagerState.currentPage) {
        onIntent(UserDetailIntent.ChangeTabIndex(pagerState.currentPage))
    }

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        isShowTopBar = true,
        header = {
            UserDetailInfo(userInfo = state.userDetailInfo)
        },
        stickyHeader = {
            SecondaryTabRow(pagerState.currentPage) {
                state.tabList.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                autoSize = TextAutoSize.StepBased(
                                    minFontSize = 6.sp,
                                    maxFontSize = 12.sp,
                                    stepSize = 1.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp,
                            )
                        }, selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = Purple200,
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        },
        onCloseClick = { onIntent(UserDetailIntent.ClickClose) }
    ) {
        if (state.tabList.isEmpty()) {
            ItemEmpty(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(Res.string.text_no_items)
            )
        } else {
            HorizontalPager(state = pagerState) { pager ->
                when (state.tabList[pager]) {
                    "PHOTOS" -> {
                        UserDetailPhotoScreen(
                            photoList = photoPaging,
                            isLoading = state.isPhotoLoading,
                            onIntent = onIntent
                        )
                    }

                    "LIKES" -> {
                        UserDetailLikeScreen(
                            photoList = likePaging,
                            isLoading = state.isLikeLoading,
                            onIntent = onIntent
                        )
                    }

                    "COLLECTIONS" -> {
                        UserDetailCollectionScreen(
                            collectionList = collectPaging,
                            isLoading = state.isCollectLoading,
                            onIntent = onIntent
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserDetailInfo(userInfo: UserDetail?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShimmerImage(
            modifier = Modifier
                .size(100.dp, 100.dp)
                .clip(RoundedCornerShape(100)),
            url = userInfo?.profileImage?.large
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .shimmer(visible = userInfo == null),
                text = userInfo?.name ?: Constants.TEXT_PREVIEW,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UserDetailInfoItem(
                    title = "Photos",
                    value = userInfo?.totalPhotos
                )

                UserDetailInfoItem(
                    title = "Likes",
                    value = userInfo?.totalLikes
                )

                UserDetailInfoItem(
                    title = "Collections",
                    value = userInfo?.totalCollections
                )
            }
        }
    }
}

@Composable
fun UserDetailInfoItem(title: String, value: Int?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier
                .shimmer(value == null),
            text = (value ?: 99999).toCommaFormat(),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}