package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens

@Composable
fun UserDetailLikeScreen(
    navController: NavHostController,
    photoList: LazyPagingItems<Photo>?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(photoList?.itemCount ?: 0) { idx ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
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