package com.chs.yoursplash.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toUnSplashUser
import com.chs.yoursplash.data.model.ResponseSearchUsers
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.User

class SearchUserPaging(
    private val api: UnSplashService,
    private val query: String,
    private val loadQuality: LoadQuality
): PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 0
            val response = api.requestUnsplash<ResponseSearchUsers>(
                url = Constants.GET_SEARCH_USERS,
                params = hashMapOf(
                    "query" to query,
                    "page" to page.toString()
                )
            ).result.map {
                it.toUnSplashUser(loadQuality)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if(response.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}