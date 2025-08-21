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
import app.cash.paging.compose.LazyPagingItems
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ShimmerImage
import com.chs.yoursplash.util.Constants

@Composable
fun UserDetailPhotoScreen(
    photoList: LazyPagingItems<Photo>?,
    loadQuality: String,
    onNavigate: (Screens) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
    ) {
        if (photoList != null) {
            items(count = photoList.itemCount) { idx ->
                ShimmerImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        ).clickable {
                            if (photoList[idx] != null) {
                                onNavigate(Screens.ImageDetailScreen(photoList[idx]!!.id))
                            }
                        },
                    url = Constants.getPhotoQualityUrl(photoList[idx]?.urls, loadQuality)
                )
            }
        }
    }
}