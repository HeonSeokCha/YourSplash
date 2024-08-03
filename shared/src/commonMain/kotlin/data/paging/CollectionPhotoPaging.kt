package data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import data.api.UnSplashService
import data.mapper.toUnSplashImage
import com.chs.yoursplash.data.model.ResponsePhoto
import domain.model.Photo

class CollectionPhotoPaging(
    private val api: UnSplashService,
    private val collectionId: String
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
            val response = api.requestUnsplash<List<ResponsePhoto>>(
                url = Constants.GET_COLLECTION_PHOTOS(collectionId),
                params = hashMapOf("page" to page.toString())
            ).map { it.toUnSplashImage() }

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