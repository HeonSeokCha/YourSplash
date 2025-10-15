package com.chs.yoursplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.model.ResponseSearchPhotos
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Orientations
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter

class SearchPhotoPaging(
    private val api: UnSplashService,
    private val query: String,
    private val searchFilter: SearchFilter,
    private val loadQuality: LoadQuality
): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val page = params.key ?: 0
            val response = api.requestUnsplash<ResponseSearchPhotos>(
                url = Constants.GET_SEARCH_PHOTOS,
                params = hashMapOf(
                    "query" to query,
                    "page" to page.toString(),
                    "order_by" to searchFilter.orderBy.rawValue,
                ).apply {
                    if (searchFilter.color != null) this["color"] = searchFilter.color

                    if (searchFilter.orientation != Orientations.Any) {
                        this["orientation"] = searchFilter.orientation.rawValue!!
                    }
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