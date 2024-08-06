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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import domain.model.Photo
import presentation.base.placeholder
import util.Constants

@Composable
fun ImageCard(
    photoInfo: Photo?,
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
                    if (photoInfo?.user?.userName != null) {
                        onClick(Constants.TARGET_USER to photoInfo.user.userName)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(100))
                    .placeholder(
                        visible = photoInfo == null,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(photoInfo?.user?.photoProfile?.large)
                    .crossfade(true)
                    .build(),
                placeholder = ColorPainter(Color.Gray),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = photoInfo?.user?.name ?: "...",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
                .clip(RoundedCornerShape(10.dp))
                .placeholder(
                    visible = photoInfo == null,
                    highlight = PlaceholderHighlight.shimmer()
                )
                .clickable {
                    if (photoInfo?.id != null) {
                        onClick(Constants.TARGET_PHOTO to photoInfo.id)
                    }
                },
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(Constants.getPhotoQualityUrl(photoInfo?.urls, loadQuality))
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(Color.LightGray),
            contentDescription = null,
        )
    }
}