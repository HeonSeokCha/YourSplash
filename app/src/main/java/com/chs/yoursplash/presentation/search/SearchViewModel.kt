package com.chs.yoursplash.presentation.search

import androidx.lifecycle.ViewModel
import com.chs.yoursplash.domain.usecase.GetSearchResultCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPhotoUseCase: GetSearchResultPhotoUseCase,
    private val searchCollectionUseCase: GetSearchResultCollectionUseCase,
    private val searchUserUseCase: GetSearchResultUserUseCase
): ViewModel() {


}