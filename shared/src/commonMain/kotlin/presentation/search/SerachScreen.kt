package com.chs.yoursplash.presentation.search

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.presentation.browse.BrowseActivity
import com.chs.yoursplash.presentation.ui.theme.Purple200
import util.Constants
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    state: SearchState,
    modalClick: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val tabList = remember { listOf("PHOTOS", "COLLECTIONS", "USERS") }
    val pagerState = rememberPagerState(initialPage = 0) { tabList.size }

    DisposableEffect(Unit) {
        onDispose {
            onBack()
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
            tabList.forEachIndexed { index, title ->
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
                        modalClick = { modalClick() }
                    ) {
                        context.startActivity(
                            Intent(context, BrowseActivity::class.java).apply {
                                putExtra(Constants.TARGET_TYPE, it.first)
                                putExtra(Constants.TARGET_ID, it.second)
                            }
                        )
                    }
                }

                1 -> {
                    SearchResultCollectionScreen(state = state) {
                        context.startActivity(
                            Intent(context, BrowseActivity::class.java).apply {
                                putExtra(Constants.TARGET_TYPE, it.first)
                                putExtra(Constants.TARGET_ID, it.second)
                            }
                        )
                    }
                }

                2 -> {
                    SearchResultUserScreen(state = state) {
                        context.startActivity(
                            Intent(context, BrowseActivity::class.java).apply {
                                putExtra(Constants.TARGET_TYPE, it.first)
                                putExtra(Constants.TARGET_ID, it.second)
                            }
                        )
                    }
                }
            }
        }
    }
}