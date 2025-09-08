package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun PhotoTagListScreenRoot(
    viewModel: PhotoTagListViewModel,
    onClose: () -> Unit,
    onNavigate: (Screens) -> Unit
) {
    val pagingItems = viewModel.pagingItems.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()

    PhotoTagListScreen(
        state = state,
        pagingItems = pagingItems,
        onClose = onClose,
        onNavigate = onNavigate
    )
}

@Composable
fun PhotoTagListScreen(
    state: PhotoTagListState,
    pagingItems: LazyPagingItems<Photo>,
    onClose: () -> Unit,
    onNavigate: (Screens) -> Unit,
) {
    val scrollState = rememberScrollState()

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        isShowTopBar = true,
        header = { },
        onCloseClick = onClose
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id }
            ) { idx ->
                ImageCard(
                    photoInfo = pagingItems[idx],
                    onPhotoClick = { onNavigate(Screens.ImageDetailScreen(it)) },
                    onUserClick = { onNavigate(Screens.UserDetailScreen(it)) }
                )
            }

//            when (resultPagingItems.loadState.refresh) {
//                is LoadState.Loading -> {
//                    items(10) {
//                        ImageCard(photoInfo = null)
//                    }
//                }
//
//                is LoadState.Error -> {
//                    item {
//                        Text(
//                            text = (resultPagingItems.loadState.refresh as LoadState.Error).error.message
//                                ?: "Unknown Error.."
//                        )
//                    }
//                }
//
//                else -> Unit
//            }
//
//            when (resultPagingItems.loadState.append) {
//                is LoadState.Loading -> {
//                    items(10) {
//                        ImageCard(photoInfo = null)
//                    }
//                }
//
//                is LoadState.Error -> {
//                    item {
//                        Text(
//                            text = (resultPagingItems.loadState.refresh as LoadState.Error).error.message
//                                ?: "Unknown Error.."
//                        )
//                    }
//                }
//
//                else -> Unit
//            }
        }
    }
}