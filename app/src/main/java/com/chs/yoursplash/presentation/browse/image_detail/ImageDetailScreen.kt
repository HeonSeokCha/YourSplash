package com.chs.yoursplash.presentation.browse.image_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.util.color
import kotlin.math.max

@Composable
fun ImageDetailScreen(
    photoId: String,
    navController: NavHostController,
    viewModel: ImageDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(context, viewModel) {
        viewModel.getImageDetailInfo(photoId)
        viewModel.getImageRelatedList(photoId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(state.imageDetailInfo?.color?.color ?: Color.White),
                contentScale = ContentScale.Crop,
                model = state.imageDetailInfo?.urls?.full ?: "",
                contentDescription = null
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
                                        "${Screens.UserDetailScreen.route}/${state.imageDetailInfo?.user?.id}"
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

                Text(
                    text = "Related photos"
                )
            }
        }

        item {
            StaggeredVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
            ) {
                for (image in state.imageRelatedList ?: listOf()) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                        model = image.urls.small_s3,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
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

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    cols: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurAbles, constraints ->

        val placeableXY: MutableMap<Placeable, Pair<Int, Int>> = mutableMapOf()

        val columnWidth = constraints.maxWidth / cols
        val itemConstraints = constraints.copy(minWidth = columnWidth)
        val colHeights = IntArray(cols) { 0 } // track each column's height
        val placeAbles = measurAbles.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            placeableXY[placeable] = Pair(columnWidth * column, colHeights[column])
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            placeAbles.forEach { placeable ->
                placeable.place(
                    x = placeableXY.getValue(placeable).first,
                    y = placeableXY.getValue(placeable).second
                )
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}