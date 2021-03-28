/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetkanto.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericktijerou.jetkanto.domain.usecase.shared.GetUiModeUseCase
import com.ericktijerou.jetkanto.domain.usecase.shared.SetDarkModeUseCase
import com.ericktijerou.jetkanto.ui.util.shareWhileObserved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    getUiModeUseCase: GetUiModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase
) : ViewModel() {
    private var syncJob: Job? = null

    val uiMode: SharedFlow<Boolean> = getUiModeUseCase.invoke()
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
        .shareWhileObserved(viewModelScope)

    fun setDarkMode(enabled: Boolean) {
        syncJob?.cancel()
        syncJob = viewModelScope.launch(Dispatchers.IO) {
            setDarkModeUseCase.invoke(enabled)
        }
    }
}
