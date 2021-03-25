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
package com.ericktijerou.jetkanto.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericktijerou.jetkanto.domain.usecase.record.GetRecordUseCase
import com.ericktijerou.jetkanto.domain.usecase.session.GetSessionUseCase
import com.ericktijerou.jetkanto.ui.entity.UserView
import com.ericktijerou.jetkanto.ui.entity.toView
import com.ericktijerou.jetkanto.ui.util.shareWhileObserved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val getRecordUseCase: GetRecordUseCase
) : ViewModel() {

    val session: SharedFlow<UserView> = getSessionUseCase.invoke()
        .distinctUntilChanged()
        .map { it.toView() }
        .shareWhileObserved(viewModelScope)
}
