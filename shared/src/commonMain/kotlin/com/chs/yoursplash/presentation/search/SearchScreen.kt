package com.chs.yoursplash.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.bottom.home.HomeEffect
import com.chs.yoursplash.presentation.ui.theme.Purple200
import kotlinx.coroutines.launch

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
                SearchEffect.NavigateBack -> onBack()
                is SearchEffect.NavigateCollectionDetail -> onBrowse(BrowseInfo.Collection(effect.id))
                is SearchEffect.NavigatePhotoDetail -> onBrowse(BrowseInfo.Photo(effect.id))
                is SearchEffect.NavigateUserDetail -> onBrowse(BrowseInfo.User(effect.name))
                is SearchEffect.ShowToast -> TODO()
            }
        }
    }

    SearchScreen(state) { event ->

    }
}


@OptIn(ExperimentalFoundationApi::class)
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
        onIntent(SearchIntent.OnChangeTabIndex(pagerState.currentPage))
    }

    DisposableEffect(Unit) {
        onDispose {
            onIntent(SearchIntent.OnBack)
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.selectIdx == 0
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
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Purple200
                    )
                }
            ) {
                state.tabList.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
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
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> {
                        SearchResultPhotoScreen(
                            state = state,
                            modalClick = { },
                            onBrowse = { onIntent(SearchIntent.Photo.ClickPhoto(it)) }
                        )
                    }

                    1 -> {
                        SearchResultCollectionScreen(
                            state = state,
                            onBrowse = { onIntent(SearchIntent.(it)) }
                        )
                    }

                    2 -> {
                        SearchResultUserScreen(
                            state = state,
                            onBrowse = { onIntent(SearchIntent.BrowseScreen(it)) }
                        )
                    }
                }
            }
        }


        if (state.showModal) {
            SearchBottomSheet(
                searchFilter = state.searchFilter,
                expanded = state.expandColorFilter,
                onClick = {
                    onIntent(SearchIntent.OnChangeSearchFilter(it))
                },
                onChangeExpanded = {

                },
                onDismiss = {
                    onIntent(SearchIntent.OnChangeShowModal)
                }
            )
        }
    }
}