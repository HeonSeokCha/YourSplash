package com.chs.yoursplash.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.presentation.ui.theme.Purple200
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun SearchScreenRoot(
    viewModel: SearchResultViewModel,
    onBrowse: (BrowseInfo) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateBrowse -> onBrowse(effect.info)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { onBack() }
    }

    SearchScreen(
        state = state,
        photoPaging = viewModel.photoPaging,
        collectPaging = viewModel.collectPaging,
        userPaging = viewModel.userPaging,
        onIntent = viewModel::changeEvent
    )
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreen(
    state: SearchState,
    photoPaging: Flow<PagingData<Photo>>,
    collectPaging: Flow<PagingData<UnSplashCollection>>,
    userPaging: Flow<PagingData<User>>,
    onIntent: (SearchIntent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var fabScale by remember { mutableStateOf(1f) }
    val pagerState = rememberPagerState(initialPage = 0) { state.tabList.size }

    LaunchedEffect(state.selectIdx) {
        pagerState.animateScrollToPage(state.selectIdx)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.currentPageOffsetFraction) {
        val offset = pagerState.currentPageOffsetFraction.absoluteValue
        val currentPage = pagerState.currentPage

        fabScale = when (currentPage) {
            0 -> {
                // 오른쪽으로 스크롤할 때 (페이지 1로 이동)
                (1f - offset).coerceIn(0f, 1f)
            }
            1 -> {
                // 왼쪽으로 스크롤할 때 (페이지 0으로 이동)
                if (offset < 0) (offset + 1f).coerceIn(0f, 1f)
                else 0f
            }
            else -> 0f
        }
    }

    Scaffold(
        floatingActionButton = {
            if (fabScale > 0f) {
                FloatingActionButton(
                    modifier = Modifier
                        .scale(fabScale)
                        .alpha(fabScale),
                    onClick = { onIntent(SearchIntent.ChangeShowModal(true)) }
                ) {
                    Icon(Icons.Filled.Search, contentDescription = "")
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                                color = Purple200,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = { onIntent(SearchIntent.ChangeTabIndex(index)) },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> {
                        SearchResultPhotoScreen(
                            state = state,
                            pagingList = photoPaging,
                            onIntent = onIntent
                        )
                    }

                    1 -> {
                        SearchResultCollectionScreen(
                            state = state,
                            pagingList = collectPaging,
                            onIntent = onIntent
                        )
                    }

                    2 -> {
                        SearchResultUserScreen(
                            state = state,
                            pagingList = userPaging,
                            onIntent = onIntent
                        )
                    }
                }
            }
        }

        if (state.showModal) {
            SearchBottomSheet(
                searchFilter = state.searchFilter,
                expanded = state.expandColorFilter,
                onIntent = onIntent
            )
        }
    }
}