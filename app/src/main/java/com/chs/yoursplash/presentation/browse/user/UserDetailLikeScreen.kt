package com.chs.yoursplash.presentation.browse.user

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun UserDetailLikeScreen(
    photoList: LazyPagingItems<Photo>?,
    loadQuality: String,
    onNavigate: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (photoList != null && photoList.itemCount != 0) {
            items(photoList.itemCount) { idx ->

                ImageCard(
                    photoInfo = photoList[idx],
                    loadQuality = loadQuality,
                    photoClickAble = {
                        onNavigate(
                            "${Screens.ImageDetailScreen.route}/${photoList[idx]?.id}"
                        )
                    }, userClickAble = {
                        onNavigate(
                            "${Screens.UserDetailScreen.route}/${photoList[idx]?.user?.userName}"
                        )
                    }
                )
            }
        }
    }
}