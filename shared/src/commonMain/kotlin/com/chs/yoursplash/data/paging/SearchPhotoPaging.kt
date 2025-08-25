package com.chs.yoursplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.model.ResponseSearchPhotos
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter

class SearchPhotoPaging(
    private val api: UnSplashService,
    private val searchFilter: SearchFilter,
    private val loadQuality: LoadQuality
): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val page = params.key ?: 1
            val response = api.requestUnsplash<ResponseSearchPhotos>(
                url = Constants.GET_SEARCH_PHOTOS,
                params = hashMapOf(
                    "query" to searchFilter.query!!,
                    "page" to page.toString(),
                    "order_by" to searchFilter.orderBy,
                ).apply {
                    if (searchFilter.color != null) this["color"] = searchFilter.color
                    if (searchFilter.orientation != null) this["orientation"] = searchFilter.orientation
                }
            ).result.map {
                it.toUnSplashImage(loadQuality)
            }

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if(response.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}