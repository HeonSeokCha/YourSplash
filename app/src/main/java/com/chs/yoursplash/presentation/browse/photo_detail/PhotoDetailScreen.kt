package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.*

@Composable
fun ImageDetailScreen(
    state: PhotoDetailState,
    onNavigate: (Screens) -> Unit
) {
//    val context = LocalContext.current
//    var downLoadQueueId: Long by remember { mutableLongStateOf(0L) }
//    if (downLoadQueueId != 0L) {
//        DownloadBroadCastReceiver(downLoadQueueId) {
//            when (it) {
//                DownLoadState.DOWNLOAD_FAILED -> {
//                    Toast.makeText(context, "Photo Download UnSuccessful...", Toast.LENGTH_SHORT)
//                        .show()
//                    viewModel.setPhotoDownloadState(DownLoadState.DOWNLOAD_FAILED)
//                }
//                DownLoadState.DOWNLOAD_SUCCESS -> {
//                    Toast.makeText(context, "Photo Download Successful...", Toast.LENGTH_SHORT)
//                        .show()
//                    viewModel.setPhotoDownloadState(DownLoadState.DOWNLOAD_SUCCESS)
//                }
//                DownLoadState.DOWNLOADING -> {
//                    Toast.makeText(context, "Photo Download Starting...", Toast.LENGTH_SHORT).show()
//                    viewModel.setPhotoDownloadState(DownLoadState.DOWNLOADING)
//                }
//            }
//        }
//    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            Column {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(((state.imageDetailInfo?.height ?: 2000) / 10).dp),
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            Constants.getPhotoQualityUrl(
                                state.imageDetailInfo?.urls,
                                state.wallpaperQuality
                            )
                        ).crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = Constants.getPlaceHolder(state.imageDetailInfo?.blurHash)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                if (state.imageDetailInfo?.user?.userName != null) {
                                    onNavigate(
                                        Screens.UserDetailScreen(state.imageDetailInfo.user.userName)
                                    )
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(100)),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(state.imageDetailInfo?.user?.photoProfile?.large)
                                .crossfade(true)
                                .build(),
                            placeholder = ColorPainter(
                                state.imageDetailInfo?.color?.color ?: Color.LightGray
                            ),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = state.imageDetailInfo?.user?.name ?: "",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

                if (state.imageDetailInfo != null) {
                    ImageDetailInfo(state.imageDetailInfo) { selectTag ->
                        onNavigate(
                            Screens.PhotoTagResultScreen(selectTag)
                        )
                    }
                }
            }
        }

        if (state.imageRelatedList.isNotEmpty()) {
            items(
                count = state.imageRelatedList.size,
                key = { state.imageRelatedList[it].id }
            ) { idx ->
                val item = state.imageRelatedList[idx]
                AsyncImage(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .clickable {
                            onNavigate(Screens.ImageDetailScreen(item.id))
                        },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.getPhotoQualityUrl(item.urls, state.loadQuality))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = Constants.getPlaceHolder(item.blurHash)
                )
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}
//
//private fun downloadPhoto(
//    context: Context,
//    photoDetail: PhotoDetail?,
//    downloadStart: (Long) -> Unit
//) {
//    val downloadUrl: String = photoDetail?.urls?.raw.toString()
//    val fileName: String = "${photoDetail?.user?.userName}-${photoDetail?.id}.jpg"
//    val saveDirPath: String = "/${context.getString(R.string.app_name)}/${fileName}"
//
//    val request = DownloadManager.Request(Uri.parse(downloadUrl))
//        .setTitle("Download YourSplash Photo")
//        .setDescription("Downloading YourSplash Photo")
//        .setMimeType("image/*")
//        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, saveDirPath)
//        .setRequiresCharging(false)
//        .setAllowedOverMetered(true)
//        .setAllowedOverRoaming(true)
//
//    val downloadManger: DownloadManager =
//        context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
//
//    downloadStart(downloadManger.enqueue(request))
//
//}