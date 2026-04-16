package com.chs.yoursplash.presentation.bottom.collection

import androidx.compose.ui.semantics.CollectionInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.ViewType
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import com.chs.yoursplash.domain.usecase.GetViewTypeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CollectionViewModel(
    getHomeCollectionsUseCase: GetHomeCollectionsUseCase,
    private val getViewTypeUseCase: GetViewTypeUseCase
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<UnSplashCollection>> = getHomeCollectionsUseCase()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(CollectionState())
    val state = _state
        .onStart {
            observeViewType()
        }.stateIn(
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

    private fun observeViewType() {
        viewModelScope.launch {
            getViewTypeUseCase().collect { viewType ->
                _state.update { it.copy(isGrid = viewType == ViewType.Grid) }
            }
        }
    }
}