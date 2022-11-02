package com.chs.yoursplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.domain.model.UnSplashCollection

class SearchCollectionPaging(
    private val api: UnSplashService
): PagingSource<Int, UnSplashCollection>() {
    override fun getRefreshKey(state: PagingState<Int, UnSplashCollection>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashCollection> {
        TODO("Not yet implemented")
    }
}