package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailPhotoScreen(
    navController: NavHostController,
    photoList: LazyPagingItems<Photo>?
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
    ) {
        items(photoList?.itemCount ?: 0) { idx ->
            AsyncImage(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 16.dp
                    )
                    .clickable {
                        navController.navigate(
                            "${Screens.ImageDetailScreen.route}/${photoList?.get(idx)?.id}"
                        )
                    },
                model = photoList?.get(idx)?.urls?.thumb,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}