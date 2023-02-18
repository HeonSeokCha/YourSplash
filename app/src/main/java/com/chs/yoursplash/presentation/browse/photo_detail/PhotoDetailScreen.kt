package com.chs.yoursplash.presentation.browse.photo_detail

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chs.yoursplash.R
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDetailScreen(
    photoId: String,
    navController: NavHostController,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    var downLoadQueueId: Long by remember { mutableStateOf(0L) }

    LaunchedEffect(context, viewModel) {
        viewModel.getImageDetailInfo(photoId)
        viewModel.getImageRelatedList(photoId)
    }

    if (downLoadQueueId != 0L) {
        DownloadBroadCastReceiver(downLoadQueueId) {
            when (it) {
                DownLoadState.DOWNLOAD_FAILED -> {
                    Toast.makeText(context, "Photo Download UnSuccessful...", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.setPhotoDownloadState(DownLoadState.DOWNLOAD_FAILED)
                }
                DownLoadState.DOWNLOAD_SUCCESS -> {
                    Toast.makeText(context, "Photo Download Successful...", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.setPhotoDownloadState(DownLoadState.DOWNLOAD_SUCCESS)
                }
                DownLoadState.DOWNLOADING -> {
                    Toast.makeText(context, "Photo Download Starting...", Toast.LENGTH_SHORT).show()
                    viewModel.setPhotoDownloadState(DownLoadState.DOWNLOADING)
                }
            }
        }
    }

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
                    placeholder = ColorPainter(state.imageDetailInfo?.color?.color ?: Color.White)
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
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        "${Screens.UserDetailScreen.route}/${state.imageDetailInfo?.user?.userName}"
                                    )
                                }
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

                    when (state.imageSaveState) {
                        PhotoSaveState.NOT_DOWNLOAD -> {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = {
                                    downloadPhoto(
                                        context,
                                        state.imageDetailInfo,
                                        downloadStart = { downLoadQueueId = it }
                                    )
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = "download"
                                )
                            }
                        }
                        PhotoSaveState.DOWNLOADING -> {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        "File is DownLoading",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Downloading,
                                    contentDescription = "downloading"
                                )
                            }
                        }
                        PhotoSaveState.DOWNLOADED -> {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        "File is DownLoaded",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }) {
                                Icon(
                                    imageVector = Icons.Default.DownloadDone,
                                    contentDescription = "fileIsSaved"
                                )
                            }
                        }
                    }
                }
                Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

                ImageDetailInfo(state.imageDetailInfo) { selectTag ->
                    navController.navigate("${Screens.PhotoTagResultScreen.route}/$selectTag")
                }
            }
        }

        if (state.imageRelatedList.isNotEmpty()) {
            items(state.imageRelatedList) { item ->
                AsyncImage(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .clickable {
                            navController.navigate(
                                "${Screens.ImageDetailScreen.route}/${item.id}"
                            )
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

private fun downloadPhoto(
    context: Context,
    photoDetail: PhotoDetail?,
    downloadStart: (Long) -> Unit
) {
    val downloadUrl: String = photoDetail?.urls?.raw.toString()
    val fileName: String = "${photoDetail?.user?.userName}-${photoDetail?.id}.jpg"
    val saveDirPath: String = "/${context.getString(R.string.app_name)}/${fileName}"

    val request = DownloadManager.Request(Uri.parse(downloadUrl))
        .setTitle("Download YourSplash Photo")
        .setDescription("Downloading YourSplash Photo")
        .setMimeType("image/*")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, saveDirPath)
        .setRequiresCharging(false)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)

    val downloadManger: DownloadManager =
        context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

    downloadStart(downloadManger.enqueue(request))

}