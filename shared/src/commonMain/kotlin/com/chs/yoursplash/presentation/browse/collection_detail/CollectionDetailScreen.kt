package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.CollapsingToolbarScaffold
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.util.Constants

@Composable
fun CollectionDetailScreen(
    state: CollectionDetailState,
    onClose: () -> Unit,
    onClick: (Pair<String, String>) -> Unit
) {
    val lazyPagingItems = state.collectionPhotos?.collectAsLazyPagingItems()
    val scrollState = rememberScrollState()

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            if (state.isError) {
                Text(state.errorMessage ?: "UnknownError")
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(visible = state.collectionDetailInfo == null),
                    text = if (state.collectionDetailInfo == null) {
                        Constants.TEXT_PREVIEW
                    } else { "${state.collectionDetailInfo.totalPhotos} Photos ● " + "Create by ${state.collectionDetailInfo.user.name}"
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        isShowTopBar = true,
        onCloseClick = onClose
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            if (lazyPagingItems != null) {
                items(count = lazyPagingItems.itemCount) { idx ->

                    ImageCard(
                        photoInfo = lazyPagingItems[idx],
                        loadQuality = state.loadQuality
                    ) {
                        onClick(it)
                    }
                }
            }
        }
    }
}