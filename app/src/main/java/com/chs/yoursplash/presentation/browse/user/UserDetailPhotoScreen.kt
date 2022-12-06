package com.chs.yoursplash.presentation.browse.user

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.BlurHashDecoder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailPhotoScreen(
    context: Context,
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
                model = photoList?.get(idx)?.urls?.small,
                placeholder = BitmapPainter(
                    BlurHashDecoder.decode(blurHash = photoList?.get(idx)?.blurHash)!!.asImageBitmap()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }

    when (photoList?.loadState?.source?.refresh) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
            Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
}