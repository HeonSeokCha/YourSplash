package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_photos
import com.chs.youranimelist.res.text_no_result
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.presentation.base.ShimmerImage
import com.chs.yoursplash.presentation.base.shimmer
import com.chs.yoursplash.presentation.pxToDp
import com.chs.yoursplash.presentation.search.SearchIntent
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserDetailPhotoScreen(
    photoList: Flow<PagingData<Photo>>,
    isLoading: Boolean,
    onIntent: (UserDetailIntent) -> Unit
) {
    val pagingItems = photoList.collectAsLazyPagingItems()
    val lazyVerticalStaggeredState = rememberLazyStaggeredGridState()
    val isEmpty by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.NotLoading
                    && pagingItems.loadState.append.endOfPaginationReached
                    && pagingItems.itemCount == 0
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> onIntent(UserDetailIntent.Photo.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    onIntent(UserDetailIntent.Photo.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> onIntent(UserDetailIntent.Photo.LoadComplete)
        }
    }

    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyVerticalStaggeredState,
        columns = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
        contentPadding = PaddingValues(4.dp)
    ) {
        when {
            isLoading -> {
                items(count = Constants.COUNT_LOADING_ITEM) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .shimmer(true)
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 16.dp
                            )
                    )
                }
            }

            isEmpty -> {
                item(span = StaggeredGridItemSpan.FullLine) {
                    ItemEmpty(
                        modifier = Modifier.fillMaxSize(),
                        text = stringResource(Res.string.text_no_photos)
                    )
                }
            }

            else -> {
                items(count = pagingItems.itemCount) { idx ->
                    val item = pagingItems[idx] ?: return@items
                    ShimmerImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio((item.width.toFloat() / item.height.toFloat()))
                            .clickable {
                                onIntent(UserDetailIntent.ClickPhoto(item.id))
                            },
                        url = item.urls
                    )
                }
            }
        }
    }
}