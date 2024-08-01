package com.chs.yoursplash.presentation.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import util.Constants
import util.SearchFilter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    searchFilter: SearchFilter,
    onClick: (SearchFilter) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectOrder by remember { mutableStateOf(searchFilter.orderBy) }
    var selectedColor by remember { mutableStateOf(searchFilter.color) }
    var selectOri by remember { mutableStateOf(searchFilter.orientation) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 16.dp,
                bottom = 16.dp
            )
    ) {
        Text(
            text = "Sort By",
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        TabRow(
            selectedTabIndex = Constants.SORT_BY_LIST.indexOf(Constants.SORT_BY_LIST.find { it.second == selectOrder }),
            containerColor = Color.LightGray
        ) {
            Constants.SORT_BY_LIST.forEach { title ->
                Row(
                    modifier = Modifier
                        .clickable(onClick = { selectOrder = title.second })
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title.first)
                }
            }
        }
        Spacer(Modifier.height(12.dp))

        Text(
            text = "Color",
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = Constants.SEARCH_COLOR_LIST.find { it.second == selectedColor }!!.first,
                onValueChange = { },
                label = { Text("Categories") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                Constants.SEARCH_COLOR_LIST.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedColor = selectionOption.second
                            expanded = false
                        }, text = {

                            Text(text = selectionOption.first)
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))

        Text(
            text = "Orientation",
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        TabRow(
            selectedTabIndex = Constants.SEARCH_ORI_LIST
                .indexOf(Constants.SEARCH_ORI_LIST.find { it.second == selectOri }),
            containerColor = Color.LightGray
        ) {
            Constants.SEARCH_ORI_LIST.forEach { title ->
                Row(
                    modifier = Modifier
                        .clickable(onClick = {
                            selectOri = title.second
                        })
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title.first,
                        maxLines = 1
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onClick = {
                Log.e("SEARCHFILTER", "$selectOrder, $selectedColor, $selectOri")
                onClick(
                    SearchFilter(
                        orderBy = selectOrder,
                        color = selectedColor,
                        orientation = selectOri
                    )
                )
            }
        ) {
            Text(text = "APPLY")
        }
    }
}