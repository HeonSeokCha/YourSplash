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
import coil.size.Size
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.BlurHashDecoder

@Composable
fun UserDetailCollectionScreen(
    context: Context,
    navController: NavHostController,
    collectionList: LazyPagingItems<UnSplashCollection>?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(collectionList?.itemCount ?: 0) { idx ->
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
                            "${Screens.CollectionDetailScreen.route}/${collectionList?.get(idx)?.id}"
                        )
                    },
                model = collectionList?.get(idx)?.previewPhotos?.get(0)?.urls?.thumb,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = BitmapPainter(
                    BlurHashDecoder.decode(blurHash = collectionList?.get(idx)?.previewPhotos?.get(idx)?.blurHash)!!.asImageBitmap()
                ),
            )
        }
    }

    when (collectionList?.loadState?.source?.refresh) {
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