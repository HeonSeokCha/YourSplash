package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.Constants

@Composable
fun UserDetailCollectionScreen(
    collectionList: LazyPagingItems<UnSplashCollection>?,
    loadQuality: String,
    onNavigate: (Screens) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (collectionList != null && collectionList.itemCount != 0) {
            items(
                count = collectionList.itemCount,
                key = collectionList.itemKey { it.id }
            ) { idx ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
                            .drawWithContent {
                                drawContent()
                                drawRect(
                                    brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black)),
                                    alpha = 0.4f
                                )
                            }
                            .clickable {
                                onNavigate(
                                    Screens.CollectionDetailScreen(collectionList[idx]!!.id)
                                )
                            },
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(Constants.getPhotoQualityUrl(collectionList[idx]?.previewPhotos?.first()?.urls, loadQuality))
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        placeholder = Constants.getPlaceHolder(collectionList[idx]?.previewPhotos?.first()?.blurHash)
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(
                                start = 16.dp,
                                bottom = 16.dp
                            )
                    ) {
                        Text(
                            text = collectionList[idx]?.title ?: "...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${collectionList[idx]?.totalPhotos ?: 0} Photos",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}