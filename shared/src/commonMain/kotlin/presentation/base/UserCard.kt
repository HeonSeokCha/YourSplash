package presentation.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import domain.model.User
import util.Constants

@Composable
fun UserCard(
    userInfo: User?,
    loadQuality: String = "Regular",
    userClickAble: (userName: String) -> Unit,
    photoClickAble: (photoId: String) -> Unit
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                userClickAble(userInfo?.userName ?: "")
            },
    ) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(100))
                .placeholder(
                    visible = userInfo == null,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(userInfo?.photoProfile?.large)
                .crossfade(true)
                .build(),
            contentDescription = null
        )

        Column {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = userInfo?.name ?: "",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                contentPadding = PaddingValues(top = 8.dp, end = 8.dp)
            ) {
                if (userInfo?.photos != null && userInfo.photos.isNotEmpty()) {
                    items(
                        count = userInfo.photos.size,
                        key = { userInfo.photos[it].id }
                    ) { idx ->
                        AsyncImage(
                            modifier = Modifier
                                .size(90.dp, 190.dp)
                                .clip(RoundedCornerShape(15))
                                .clickable {
                                    photoClickAble(userInfo.photos[idx].id)
                                },
                            model = ImageRequest.Builder(LocalPlatformContext.current)
                                .data(
                                    Constants.getPhotoQualityUrl(
                                    userInfo.photos[idx].urls,
                                    loadQuality
                                ))
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
    }
}