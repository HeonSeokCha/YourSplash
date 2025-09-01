package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chs.yoursplash.domain.model.Orientations
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.model.SortType
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.launch
import kotlin.collections.indexOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    searchFilter: SearchFilter,
    onClick: (SearchFilter) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    var selectOrder by remember { mutableStateOf(searchFilter.orderBy) }
    var selectedColor by remember { mutableStateOf(searchFilter.color) }
    var selectOri by remember { mutableStateOf(searchFilter.orientation) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 15.dp, horizontal = 15.dp)
        ) {
            Text(
                text = "Sort By",
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            TabRow(
                selectedTabIndex = SortType.entries.indexOf(selectOrder),
                containerColor = Color.LightGray
            ) {
                Constants.SORT_BY_LIST.forEach { info ->
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    selectOrder = SortType.entries.find { it.name == info.first }!!
                                }
                            )
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = info.first)
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
                selectedTabIndex = Orientations.entries.indexOf(selectOri),
                containerColor = Color.LightGray
            ) {
                Constants.SEARCH_ORI_LIST.forEach { info ->
                    Row(
                        modifier = Modifier
                            .clickable(onClick = {
                                selectOri = Orientations.entries.find { it.name == info.first }!!
                            })
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = info.first,
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
                    onClick(
                        SearchFilter(
                            orderBy = selectOrder,
                            color = selectedColor,
                            orientation = selectOri
                        )
                    )
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                }
            ) {
                Text(text = "APPLY")
            }
        }
    }
}