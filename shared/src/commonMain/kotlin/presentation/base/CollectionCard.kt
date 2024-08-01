package presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.presentation.base.PlaceholderHighlight
import com.chs.yoursplash.presentation.base.placeholder
import com.chs.yoursplash.presentation.base.shimmer
import util.Constants

@Composable
fun CollectionInfoCard(
    collectionInfo: UnSplashCollection?,
    loadQuality: String = "Regular",
    onClick: (Pair<String, String>) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 4.dp,
                    bottom = 8.dp
                )
                .clickable {
                    if (collectionInfo != null) {
                        onClick(Constants.TARGET_USER to collectionInfo.user.userName)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100))
                    .placeholder(
                        visible = collectionInfo == null,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(collectionInfo?.user?.photoProfile?.large)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = collectionInfo?.user?.name ?: "...",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        CollectionCard(
            collectionInfo = collectionInfo,
            loadQuality = loadQuality
        ) {
            if (collectionInfo != null) {
                onClick(Constants.TARGET_COLLECTION to it)
            }
        }
    }
}

@Composable
fun CollectionSimpleCard(
    collectionInfo: UnSplashCollection?,
    loadQuality: String,
    collectionClickAble: (collectionId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        CollectionCard(collectionInfo = collectionInfo, loadQuality = loadQuality) {
            collectionClickAble(it)
        }
    }
}

@Composable
private fun CollectionCard(
    collectionInfo: UnSplashCollection?,
    loadQuality: String,
    collectionClickAble: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                if (collectionInfo != null) {
                    collectionClickAble(collectionInfo.id)
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 4.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                    .placeholder(
                        visible = collectionInfo == null,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        Constants.getPhotoQualityUrl(
                            collectionInfo?.previewPhotos?.get(0)?.urls,
                            loadQuality
                        )
                    )
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                placeholder = Constants.getPlaceHolder(collectionInfo?.previewPhotos?.first()?.blurHash)
            )
        }

        Column(
            modifier = Modifier
                .weight(4f)
        ) {
            repeat(2) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(125.dp)
                        .padding(bottom = 2.dp)
                        .clip(RoundedCornerShape(topEnd = 10.dp))
                        .placeholder(
                            visible = collectionInfo == null,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            Constants.getPhotoQualityUrl(
                                collectionInfo?.previewPhotos?.get(it + 1)?.urls,
                                loadQuality
                            )
                        )
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    placeholder = ColorPainter(Color.LightGray)
                )
            }
        }
    }
}