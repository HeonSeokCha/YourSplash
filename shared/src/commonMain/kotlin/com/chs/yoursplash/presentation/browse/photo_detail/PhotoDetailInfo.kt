package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.model.UnSplashTag
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.presentation.toCommaFormat
import com.chs.yoursplash.presentation.toComposeColor
import com.chs.yoursplash.util.Constants
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageDetailInfo(
    imageDetailInfo: PhotoDetail?,
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
            ItemDetailValue(title = "Views", value = imageDetailInfo?.views?.toCommaFormat())
            ItemDetailValue(
                title = "Downloads",
                value = imageDetailInfo?.downloads?.toCommaFormat()
            )
            ItemDetailValue(title = "Likes", value = imageDetailInfo?.likes?.toCommaFormat())
        }

        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

        RelatedTags(
            list = imageDetailInfo?.tags,
        ) {
            tagClick(it)
        }
    }
}

@Composable
private fun ItemDetailValue(
    title: String,
    value: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .shimmer(value == null),
            text = title,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .shimmer(value == null),
            text = value ?: Constants.TEXT_PREVIEW,
            fontWeight = FontWeight.Light
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RelatedTags(
    list: List<UnSplashTag>?,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        if (list?.isNotEmpty() == true) {
            Text(
                text = "Related tags",
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (list == null) {
                repeat(6) {
                    SuggestionChip(
                        modifier = Modifier
                            .size(48.dp, 24.dp)
                            .shimmer(true)
                            .padding(horizontal = 2.dp),
                        onClick = {},
                        label = {},
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.LightGray,
                            labelColor = Color.White
                        ), border = AssistChipDefaults.assistChipBorder(
                            enabled = true,
                            borderColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            } else {
                list.forEach { info ->
                    SuggestionChip(
                        modifier = Modifier
                            .wrapContentWidth()
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
    }
}