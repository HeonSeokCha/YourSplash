package presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import presentation.base.ImageCard
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot

@Composable
fun SearchResultPhotoScreen(
    state: SearchState,
    modalClick: () -> Unit,
    clickable: (Pair<String, String>) -> Unit
) {
    val pagingList = state.searchPhotoList?.collectAsLazyPagingItems()
    val scrollState = rememberLazyListState()

    LaunchedEffect(state.searchQuery) {
        if (state.searchQuery != null) {
            snapshotFlow { state.searchQuery }
                .distinctUntilChanged()
                .filterNot { it.isNotEmpty() }
                .collect {
                    scrollState.scrollToItem(0)
                }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        if (pagingList != null) {
            items(count = pagingList.itemCount) { idx ->
                val item = pagingList[idx]
                ImageCard(
                    photoInfo = item,
                    loadQuality = state.loadQuality,
                ) {
                    clickable(it)
                }
            }

            when (pagingList.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> Unit
            }

            when (pagingList.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ImageCard(photoInfo = null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun SearchFloatingActionButton(
    extend: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
            )

            AnimatedVisibility(extend) {
                Text(
                    text = "FILTER",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
