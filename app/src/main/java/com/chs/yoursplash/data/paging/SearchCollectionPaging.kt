package com.chs.yoursplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toPhotoCollection
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.model.ResponseSearchCollections
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.util.Constants

class SearchCollectionPaging(
    private val api: UnSplashService,
    private val query: String
): PagingSource<Int, UnSplashCollection>() {
    override fun getRefreshKey(state: PagingState<Int, UnSplashCollection>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashCollection> {
        return try {
            val page = params.key ?: 1
            val response = (api.requestUnsplash(
                Constants.SEARCH_COLLECTION,
                hashMapOf(
                    "query" to query,
                    "page" to page.toString()
                )
            ) as ResponseSearchCollections).result.map {
                it.toPhotoCollection()
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