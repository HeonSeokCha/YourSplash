package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import com.chs.yoursplash.presentation.ui.theme.Purple200
import kotlinx.coroutines.launch

@Composable
fun SearchScreenRoot(
    viewModel: SearchResultViewModel,
    onBrowse: (BrowseInfo) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(state) { event ->
        when (event) {
            is SearchEvent.BrowseScreen -> onBrowse(event.info)
            SearchEvent.OnBack -> onBack()
            else -> viewModel.changeEvent(event)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) { state.tabList.size }

    LaunchedEffect(state.selectIdx) {
        pagerState.animateScrollToPage(state.selectIdx)
    }

    LaunchedEffect(pagerState.currentPage) {

        onEvent(SearchEvent.TabIndex(pagerState.currentPage))
    }

    DisposableEffect(Unit) {
        onDispose {
            onEvent(SearchEvent.OnBack)
        }
    }

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
                        onBrowse = { onEvent(SearchEvent.BrowseScreen(it)) }
                    )
                }

                1 -> {
                    SearchResultCollectionScreen(
                        state = state,
                        onBrowse = { onEvent(SearchEvent.BrowseScreen(it)) }
                    )
                }

                2 -> {
                    SearchResultUserScreen(
                        state = state,
                        onBrowse = { onEvent(SearchEvent.BrowseScreen(it)) }
                    )
                }
            }
        }
    }
}