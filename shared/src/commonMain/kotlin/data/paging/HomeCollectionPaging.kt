package data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import data.api.UnSplashService
import data.mapper.toPhotoCollection
import com.chs.yoursplash.data.model.ResponseCollection
import domain.model.UnSplashCollection

class HomeCollectionPaging(
    private val api: UnSplashService
) : PagingSource<Int, UnSplashCollection>() {
    override fun getRefreshKey(state: PagingState<Int, UnSplashCollection>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashCollection> {
        return try {
            val page = params.key ?: 1
            val response: List<UnSplashCollection> = api.requestUnsplash<List<ResponseCollection>>(
                url = Constants.GET_COLLECTION,
                params = hashMapOf("page" to page.toString())
            ).map { it.toPhotoCollection() }

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}