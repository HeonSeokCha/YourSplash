package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.model.UnSplashTag
import com.chs.yoursplash.presentation.toCommaFormat

@Composable
fun ImageDetailInfo(
    imageDetailInfo: PhotoDetail,
    tagClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ItemDetailValue(title = "Views", value = imageDetailInfo.views.toCommaFormat())
            ItemDetailValue(title = "Downloads", value = imageDetailInfo.downloads.toCommaFormat())
            ItemDetailValue(title = "Likes", value = imageDetailInfo.likes.toCommaFormat())
        }

        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

        if (imageDetailInfo.tags.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Related tags",
                )

                Spacer(modifier = Modifier.height(8.dp))

                RelatedTags(
                    list = imageDetailInfo.tags,
                    color = imageDetailInfo.color
                ) {
                    tagClick(it)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ItemDetailValue(
    title: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            fontWeight = FontWeight.Light
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RelatedTags(
    list: List<UnSplashTag>,
    color: String,
    onClick: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 2.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        list.forEach { info ->
            SuggestionChip(
                modifier = Modifier
                    .height(24.dp)
                    .padding(horizontal = 2.dp),
                onClick = { onClick(info.title) },
                label = {
                    Text(
                        text = info.title,
                        fontSize = 12.sp
                    )
                }, colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.LightGray,
                    labelColor = Color.White
                ), border = AssistChipDefaults.assistChipBorder(
                    enabled = true,
                    borderColor = Color.LightGray
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}
