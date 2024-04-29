package com.chs.yoursplash.presentation.browse.user

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.BlurHashDecoder
import com.chs.yoursplash.util.Constants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetailPhotoScreen(
    context: Context,
    navController: NavHostController,
    photoList: LazyPagingItems<Photo>?,
    loadQuality: String
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
    ) {
        if (photoList != null && photoList.itemCount != 0) {
            items(
                count = photoList.itemCount,
                key = { photoList.itemKey { it.id } }
            ) { idx ->
                AsyncImage(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 16.dp
                        ).clickable {
                            navController.navigate(
                                "${Screens.ImageDetailScreen.route}/${photoList[idx]?.id}"
                            )
                        },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.getPhotoQualityUrl(photoList[idx]?.urls, loadQuality))
                        .crossfade(true)
                        .build(),
                    placeholder = Constants.getPlaceHolder(photoList[idx]?.blurHash),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
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