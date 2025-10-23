package com.chs.yoursplash.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.TextAutoSize
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
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.chs.yoursplash.presentation.ui.theme.Purple200


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    searchFilter: SearchFilter,
    expanded: Boolean,
    onIntent: (SearchIntent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var selectFilterValue by remember { mutableStateOf(searchFilter) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onIntent(SearchIntent.ChangeShowModal(false)) }
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

            SecondaryTabRow(selectedTabIndex = SortType.entries.indexOf(selectFilterValue.orderBy)) {
                Constants.SORT_BY_LIST.forEach { info ->
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    selectFilterValue = selectFilterValue.copy(
                                        orderBy = SortType.entries.find { it.name == info.first }!!
                                    )
                                }
                            )
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = info.first,
                            color = Purple200,
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = 6.sp,
                                maxFontSize = 12.sp,
                                stepSize = 1.sp
                            )
                        )
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
                onExpandedChange = { onIntent(SearchIntent.ChangeExpandColorFilter(it)) }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                    value = Constants.SEARCH_COLOR_LIST.find { it.second == selectFilterValue.color }!!.first,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = { Text("Color") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedPlaceholderColor = Color.White,
                        focusedPlaceholderColor = Color.White
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { onIntent(SearchIntent.ChangeExpandColorFilter(false)) }
                ) {
                    Constants.SEARCH_COLOR_LIST.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectFilterValue = selectFilterValue.copy(color = selectionOption.second)
                                onIntent(SearchIntent.ChangeExpandColorFilter(false))
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

            SecondaryTabRow(selectedTabIndex = Orientations.entries.indexOf(selectFilterValue.orientation)) {
                Constants.SEARCH_ORI_LIST.forEach { info ->
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    selectFilterValue = selectFilterValue.copy(
                                        orientation = Orientations.entries.find { it.name == info.first }!!
                                    )
                                }
                            )
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = info.first,
                            maxLines = 1,
                            color = Purple200,
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = 6.sp,
                                maxFontSize = 12.sp,
                                stepSize = 1.sp
                            )
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
                    onIntent(SearchIntent.ChangeSearchFilter(selectFilterValue))
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onIntent(SearchIntent.ChangeShowModal(false))
                        }
                    }
                }
            ) {
                Text(text = "APPLY")
            }
        }
    }
}