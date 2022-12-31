package com.chs.yoursplash.presentation.browse.user

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.util.BlurHashDecoder

@Composable
fun UserDetailLikeScreen(
    context: Context,
    navController: NavHostController,
    photoList: LazyPagingItems<Photo>?,
    loadQuality: String
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(photoList?.itemCount ?: 0) { idx ->
            ImageCard(
                photoInfo = photoList?.get(idx),
                loadQuality = loadQuality,
                photoClickAble = {
                    navController.navigate(
                        "${Screens.ImageDetailScreen.route}/${photoList?.get(idx)?.id}"
                    )
                }, userClickAble = {
                    navController.navigate(
                        "${Screens.UserDetailScreen.route}/${photoList?.get(idx)?.user?.userName}"
                    )
                }
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