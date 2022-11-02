package com.chs.yoursplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.domain.model.User

class SearchUserPaging(
    private val api: UnSplashService
): PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        TODO("Not yet implemented")
    }
}