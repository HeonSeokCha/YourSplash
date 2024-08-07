package data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import data.api.UnSplashService
import data.mapper.toUnSplashUser
import com.chs.yoursplash.data.model.ResponseSearchUsers
import domain.model.User

class SearchUserPaging(
    private val api: UnSplashService,
    private val query: String
): PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response = api.requestUnsplash<ResponseSearchUsers>(
                url = Constants.GET_SEARCH_USERS,
                params = hashMapOf(
                    "query" to query,
                    "page" to page.toString()
                )
            ).result.map {
                it.toUnSplashUser()
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