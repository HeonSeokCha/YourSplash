package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun PhotoTagListScreen(
    state: PhotoTagListState,
    onNavigateUser: (Screens.UserDetailScreen) -> Unit,
    onNavigatePhoto: (Screens.ImageDetailScreen) -> Unit
) {
    val resultPagingItems = state.tagSearchResultList?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        if (resultPagingItems != null && resultPagingItems.itemCount != 0) {
            items(
                count = resultPagingItems.itemCount,
                key = resultPagingItems.itemKey { it.id }
            ) {idx ->
                ImageCard(
                    photoInfo = resultPagingItems[idx],
                    loadQuality = state.loadQuality,
                    userClickAble = { userName ->
                        onNavigateUser(Screens.UserDetailScreen(userName))
                    }, photoClickAble = { photoId ->
                        onNavigatePhoto(Screens.ImageDetailScreen(photoId))
                    }
                )
            }
        }
    }
}