package com.chs.yoursplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.model.ResponseSearchPhotos
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.util.Constants

class SearchPhotoPaging(
    private val api: UnSplashService,
    private val query: String,
    private val orderBy: String,
    private val color: String?,
    private val orientation: String?
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
            val response = (api.requestUnsplash(
                Constants.GET_SEARCH_PHOTOS,
                hashMapOf(
                    "query" to query,
                    "page" to page.toString(),
                    "order_by" to orderBy,
                ).apply {
                    if (color != null) this["color"] = color
                    if (orientation != null) this["orientation"] = orientation
                }
            ) as ResponseSearchPhotos).result.map {
                it.toUnSplashImage()
            }
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if(response.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}