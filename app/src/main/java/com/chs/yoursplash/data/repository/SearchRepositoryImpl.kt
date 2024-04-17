package com.chs.yoursplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.paging.SearchCollectionPaging
import com.chs.yoursplash.data.paging.SearchPhotoPaging
import com.chs.yoursplash.data.paging.SearchUserPaging
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val client: UnSplashService
) : SearchRepository {

    override fun getSearchResultPhoto(
        query: String,
        orderBy: String,
        color: String?,
        orientation: String?
    ): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchPhotoPaging(
                api = client,
                query = query,
                orderBy = orderBy,
                color = color,
                orientation = orientation
            )
        }.flow
    }

    override fun getSearchResultCollection(query: String): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchCollectionPaging(client, query)
        }.flow
    }

    override fun getSearchResultUser(query: String): Flow<PagingData<User>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchUserPaging(client, query)
        }.flow
    }
}