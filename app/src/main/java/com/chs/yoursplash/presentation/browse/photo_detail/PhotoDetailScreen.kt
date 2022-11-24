package com.chs.yoursplash.presentation.browse.photo_detail

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.yoursplash.R
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.BlurHashDecoder
import com.chs.yoursplash.util.color
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDetailScreen(
    photoId: String,
    navController: NavHostController,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    LaunchedEffect(context, viewModel) {
        viewModel.getImageDetailInfo(photoId)
        viewModel.getImageRelatedList(photoId)
    }
    BoxWithConstraints {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = scrollState
        ) {

            item {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(((state.imageDetailInfo?.height ?: 2000) / 10).dp),
                    contentScale = ContentScale.Crop,
                    model = state.imageDetailInfo?.urls?.full ?: "",
                    contentDescription = null,
                    placeholder = ColorPainter(state.imageDetailInfo?.color?.color ?: Color.White)
                )

                Column(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
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
                                model = state.imageDetailInfo?.user?.photoProfile?.large,
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

                        IconButton(
                            modifier = Modifier.size(24.dp),
                            onClick = {
                                if (state.imageSaveState != DownLoadState.DOWNLOADING) {
                                    Toast.makeText(context, "Image Download Start..", Toast.LENGTH_SHORT).show()
                                    downloadPhoto(context, state.imageDetailInfo)

                                } else {
                                    Toast.makeText(context, "Image Downloading..", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                            when (state.imageSaveState) {
                                DownLoadState.NOT_DOWNLOAD -> {
                                    Icon(
                                        imageVector = Icons.Default.Download,
                                        contentDescription = "download"
                                    )
                                }
                                DownLoadState.DOWNLOADING -> {
                                    Icon(
                                        imageVector = Icons.Default.Downloading,
                                        contentDescription = "downloading"
                                    )
                                }
                                DownLoadState.DOWNLOADED -> {
                                    Icon(
                                        imageVector = Icons.Default.DownloadDone,
                                        contentDescription = "fileIsSaved"
                                    )
                                }
                            }
                        }
                    }
                    Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

                    ImageDetailInfo(state.imageDetailInfo)
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .height(maxHeight)
                        .nestedScroll(remember {
                            object : NestedScrollConnection {
                                override fun onPreScroll(
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    return if (available.y > 0) Offset.Zero else Offset(
                                        x = 0f,
                                        y = -scrollState.dispatchRawDelta(-available.y)
                                    )
                                }
                            }
                        }),
                ) {
                    if (state.imageRelatedList.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp, bottom = 16.dp),
                            text = "Related photos",
                            fontWeight = FontWeight.Bold
                        )
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .fillMaxSize(),
                            columns = StaggeredGridCells.Fixed(2),
                        ) {
                            items(state.imageRelatedList, key = { photo ->
                                photo.id
                            }) { photo ->
                                AsyncImage(
                                    modifier = Modifier
                                        .padding(
                                            end = 16.dp,
                                            bottom = 16.dp
                                        )
                                        .clickable {
                                            navController.navigate(
                                                "${Screens.ImageDetailScreen.route}/${photo.id}"
                                            )
                                        },
                                    model = photo.urls.small_s3,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    placeholder = if (photo.blurHash != null) {
                                        BitmapPainter(BlurHashDecoder.decode(blurHash = photo.blurHash)!!.asImageBitmap())
                                    } else ColorPainter(photo.color.color),
                                )
                            }
                        }
                    }

                    if (!state.imageDetailInfo?.relatedCollection?.result.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            text = "Related Collections",
                            fontWeight = FontWeight.Bold
                        )

                        LazyVerticalGrid(
                            modifier = Modifier
                                .wrapContentHeight(),
                            columns = GridCells.Fixed(2)
                        ) {
                            items(state.imageDetailInfo?.relatedCollection?.result?.size ?: 0) { idx ->
                                AsyncImage(
                                    modifier = Modifier
                                        .size(200.dp, 100.dp)
                                        .clickable {
                                            navController.navigate(
                                                "${Screens.CollectionDetailScreen.route}/" +
                                                        "${state.imageDetailInfo?.relatedCollection?.result?.get(idx)?.id}"
                                            )
                                        },
                                    model = state.imageDetailInfo?.relatedCollection?.result?.get(idx)?.previewPhotos?.get(0)?.urls?.small_s3,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    placeholder = BitmapPainter(
                                        BlurHashDecoder.decode(
                                            blurHash = state.imageDetailInfo?.relatedCollection?.result?.get(idx)?.previewPhotos?.get(0)?.blurHash)!!.asImageBitmap()
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
        }
    }
}

private fun downloadPhoto(
    context: Context,
    photoDetail: PhotoDetail?
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

    val downloadManger: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

    downloadManger.enqueue(request)

}