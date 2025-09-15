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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.ui.theme.Purple200
import kotlinx.coroutines.launch

@Composable
fun SearchScreenRoot(
    viewModel: SearchResultViewModel,
    onBrowse: (BrowseInfo) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateBrowse -> onBrowse(effect.info)
            }
        }
    }

    SearchScreen(
        state = state,
        onIntent = viewModel::changeEvent
    )
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreen(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) { state.tabList.size }

    LaunchedEffect(state.selectIdx) {
        pagerState.animateScrollToPage(state.selectIdx)
    }

    LaunchedEffect(pagerState.currentPage) {
        onIntent(SearchIntent.ChangeTabIndex(pagerState.currentPage))
    }

    DisposableEffect(Unit) {
        onDispose {
            onIntent(SearchIntent.ChangeSearchQuery(""))
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.selectIdx == 0,
                enter = fadeIn() ,
                exit = fadeOut(),
            ) {
                FloatingActionButton(
                    onClick = { onIntent(SearchIntent.ChangeShowModal) }
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
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
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
                            onBrowse = { onIntent(SearchIntent.ClickBrowseInfo(it)) }
                        )
                    }

                    1 -> {
                        SearchResultCollectionScreen(
                            state = state,
                            onBrowse = { onIntent(SearchIntent.ClickBrowseInfo(it)) }
                        )
                    }

                    2 -> {
                        SearchResultUserScreen(
                            state = state,
                            onBrowse = { onIntent(SearchIntent.ClickBrowseInfo(it)) }
                        )
                    }
                }
            }
        }

        if (state.showModal) {
            SearchBottomSheet(
                searchFilter = state.searchFilter,
                expanded = state.expandColorFilter,
                onClick = { onIntent(SearchIntent.ChangeSearchFilter(it)) },
                onChangeExpanded = { onIntent(SearchIntent.ChangeExpandColorFilter) },
                onDismiss = { onIntent(SearchIntent.ChangeShowModal) }
            )
        }
    }
}