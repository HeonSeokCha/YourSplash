package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.base.ImageCard

@Composable
fun CollectionDetailScreen(
    collectionId: String,
    navController: NavHostController,
    viewModel: CollectionDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val lazyPagingItems = viewModel.getCollectionPhotos(collectionId).collectAsLazyPagingItems()

    LaunchedEffect(context, viewModel) {
        viewModel.getCollectionDetail(collectionId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {

        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${state.collectionDetailInfo?.totalPhotos ?: 0} Photos ● " +
                        "Create by ${state.collectionDetailInfo?.user?.name ?: ""}",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        items(lazyPagingItems) { photoInfo ->
            ImageCard(
                photoInfo = photoInfo,
                userClickAble = { userName ->
                    navController.navigate(
                        "${Screens.UserDetailScreen.route}/$userName"
                    )
                }, photoClickAble = { photoId ->
                    navController.navigate(
                        "${Screens.ImageDetailScreen.route}/$photoId"
                    )
                }
            )
        }
    }
}