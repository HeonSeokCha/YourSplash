package com.chs.yoursplash.presentation.browse.image_detail

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.color
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDetailScreen(
    photoId: String,
    navController: NavHostController,
    viewModel: ImageDetailViewModel = hiltViewModel()
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

                        if (state.isDownloading) {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = {
                                    // TODO: show alert again
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Downloading,
                                    contentDescription = "downloading"
                                )
                            }

                        } else if (state.isSavedFile) {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = { }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DownloadDone,
                                    contentDescription = "fileIsSaved"
                                )
                            }
                        } else {
                            IconButton(
                                modifier = Modifier.size(32.dp),
                                onClick = {
                                    // TODO: file download start.
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = "download"
                                )
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
                                    model = photo.urls.thumb,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
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
                                        },
                                    model = state.imageDetailInfo?.relatedCollection?.result?.get(idx)?.previewPhotos?.get(idx)?.urls?.thumb,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
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