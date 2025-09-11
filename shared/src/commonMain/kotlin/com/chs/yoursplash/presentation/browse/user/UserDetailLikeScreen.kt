package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard
import kotlinx.coroutines.flow.Flow

@Composable
fun UserDetailLikeScreen(
    photoList: Flow<PagingData<Photo>>,
    onNavigate: (Screens) -> Unit
) {
    val pagingItems = photoList.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
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
    }
}