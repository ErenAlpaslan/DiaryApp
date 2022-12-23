package com.easylife.diary.feature.splash

import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.preferences.PreferencesKeys
import com.easylife.diary.core.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Created by erenalpaslan on 19.12.2022
 */
@HiltViewModel
class SplashViewModel(
    private val preferencesManager: PreferencesManager
): BaseViewModel() {

    private val shouldShowOnBoarding: Flow<Boolean> = flow {
        emit(preferencesManager.getBoolean(PreferencesKeys.IS_FIRST_ENTER))
    }

    val uiState: StateFlow<SplashUiState> = shouldShowOnBoarding.map {
        delay(2000)
        if (it)
            SplashUiState.NewComer
        else
            SplashUiState.OnBoardedUser
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SplashUiState.Loading
    )

}