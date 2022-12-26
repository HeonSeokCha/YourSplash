package com.chs.yoursplash.presentation.setting

import androidx.lifecycle.ViewModel
import com.chs.yoursplash.domain.usecase.GetStringPrefUseCase
import com.chs.yoursplash.domain.usecase.PutStringPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getStringPrefUseCase: GetStringPrefUseCase,
    private val putStringPrefUseCase: PutStringPrefUseCase
) : ViewModel() {

    init {

    }
}