package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chs.yoursplash.domain.model.PhotoDetail

@OptIn(ExperimentalLayoutApi::class)
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Views",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = imageDetailInfo?.views.toString(),
                    fontWeight = FontWeight.Light
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Downloads",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = imageDetailInfo?.downloads.toString(),
                    fontWeight = FontWeight.Light
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Views",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = imageDetailInfo?.likes.toString(),
                    fontWeight = FontWeight.Light
                )
            }
        }
        Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))



        if (!imageDetailInfo?.tags.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "Related tags",
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow {
                    imageDetailInfo?.tags?.filter { it.type == "search" }?.forEach { tag ->
                        AssistChip(
                            modifier = Modifier.padding(end = 8.dp),
                            onClick = { tagClick(tag.title)},
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color.LightGray,
                                labelColor = Color.Black
                            ), label = { Text(tag.title) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
