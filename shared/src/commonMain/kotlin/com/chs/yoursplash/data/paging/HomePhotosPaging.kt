package com.chs.yoursplash.data.paging

import androidx.paging.PagingSource import androidx.paging.PagingState
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.model.ResponsePhoto
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo

class HomePhotosPaging(
    private val api: UnSplashService,
    private val loadQuality: LoadQuality
) : PagingSource<Int, Photo>() {
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
                url = Constants.GET_PHOTOS,
                params = hashMapOf("page" to page.toString())
            ).map { it.toUnSplashImage(loadQuality) }

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}