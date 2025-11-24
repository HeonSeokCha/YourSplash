package com.chs.yoursplash.presentation.browse.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.text_no_photos
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.base.ImageCard
import com.chs.yoursplash.presentation.base.ItemEmpty
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserDetailLikeScreen(
    photoList: Flow<PagingData<Photo>>,
    isLoading: Boolean,
    onIntent: (UserDetailIntent) -> Unit
) {
    val pagingItems = photoList.collectAsLazyPagingItems()
    val isEmpty by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.NotLoading
                    && pagingItems.loadState.append.endOfPaginationReached
                    && pagingItems.itemCount == 0
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> onIntent(UserDetailIntent.Like.Loading)

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                    onIntent(UserDetailIntent.Like.OnError(this.message))
                }
            }

            is LoadState.NotLoading -> onIntent(UserDetailIntent.Like.LoadComplete)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        when {
            isLoading -> {
                items(count = Constants.COUNT_LOADING_ITEM) {
                    ImageCard(null)
                }
            }

            isEmpty -> {
                item {
                    ItemEmpty(
                        modifier = Modifier.fillParentMaxSize(),
                        text = stringResource(Res.string.text_no_photos)
                    )
                }
            }

            else -> {
                items(count = pagingItems.itemCount) { idx ->
                    ImageCard(
                        photoInfo = pagingItems[idx],
                        onPhotoClick = { onIntent(UserDetailIntent.ClickPhoto(it)) },
                        onUserClick = { onIntent(UserDetailIntent.ClickUser(it))}
                    )
                }
            }
        }
    }
}