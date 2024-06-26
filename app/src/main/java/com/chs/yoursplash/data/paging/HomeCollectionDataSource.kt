package com.chs.yoursplash.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toPhotoCollection
import com.chs.yoursplash.data.model.ResponseCollection
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.util.Constants

class HomeCollectionDataSource(
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
            Log.e("CHS_LOG", e.message.toString())
            LoadResult.Error(e)
        }
    }
}