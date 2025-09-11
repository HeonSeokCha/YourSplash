package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ShimmerImage
import kotlinx.coroutines.flow.Flow

@Composable
fun UserDetailPhotoScreen(
    photoList: Flow<PagingData<Photo>>,
    onNavigate: (Screens) -> Unit
) {
    val pagingItems = photoList.collectAsLazyPagingItems()
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
    ) {
        items(count = pagingItems.itemCount) { idx ->
            ShimmerImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 16.dp
                    ).clickable {
                        if (pagingItems[idx] != null) {
                            onNavigate(Screens.ImageDetailScreen(pagingItems[idx]!!.id))
                        }
                    },
                url = pagingItems[idx]?.urls
            )
        }
    }
}