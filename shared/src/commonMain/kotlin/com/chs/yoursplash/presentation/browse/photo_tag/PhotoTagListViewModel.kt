package com.chs.yoursplash.presentation.browse.photo_tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.presentation.browse.photo_tag.PhotoTagEffect.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class PhotoTagListViewModel(
    tagName: String,
    getSearchResultPhotoUseCase: GetSearchResultPhotoUseCase,
) : ViewModel() {

    val pagingItems: Flow<PagingData<Photo>> = getSearchResultPhotoUseCase(
        query = tagName,
        searchFilter = SearchFilter()
    )
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(PhotoTagListState())
    val state = _state.asStateFlow()

    private val _effect = Channel<PhotoTagEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    fun handleIntent(intent: PhotoTagIntent) {
        when (intent) {
            PhotoTagIntent.ClickClose -> _effect.trySend(Close)
            is PhotoTagIntent.ClickPhoto -> {
                _effect.trySend(NavigatePhotoDetail(intent.id))
            }

            is PhotoTagIntent.ClickUser -> {
                _effect.trySend(NavigateUser(intent.name))
            }

            is PhotoTagIntent.OnError -> _effect.trySend(ShowToast(intent.error))
            PhotoTagIntent.LoadComplete -> _state.update { it.copy(isLoading = false) }
            PhotoTagIntent.Loading -> _state.update { it.copy(isLoading = true) }
            PhotoTagIntent.RefreshData -> Unit
        }
    }
}