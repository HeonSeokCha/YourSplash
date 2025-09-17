package com.chs.yoursplash.presentation.bottom.collection

import androidx.compose.ui.semantics.CollectionInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class CollectionViewModel(
    getHomeCollectionsUseCase: GetHomeCollectionsUseCase
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<UnSplashCollection>> = getHomeCollectionsUseCase()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(CollectionState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    private val _effect = Channel<CollectionEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: CollectionIntent) {
        when (intent) {
            CollectionIntent.Loading -> updateState { it.copy(isLoading = true) }

            is CollectionIntent.ClickCollection -> {
                _effect.trySend(CollectionEffect.NavigateCollectionDetail(intent.id))
            }

            is CollectionIntent.ClickUser -> {
                _effect.trySend(CollectionEffect.NavigateUserDetail(intent.name))
            }

            CollectionIntent.LoadComplete -> {
                updateState {
                    it.copy(
                        isRefresh = false,
                        isLoading = false
                    )
                }
            }

            CollectionIntent.RefreshData -> {
                updateState {
                    it.copy(isRefresh = true)
                }
            }

            is CollectionIntent.OnError -> {
            }
        }
    }

    private fun updateState(reducer: (CollectionState) -> CollectionState) {
        _state.value = reducer(_state.value)
    }
}