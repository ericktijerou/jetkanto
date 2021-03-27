package com.ericktijerou.jetkanto.ui.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericktijerou.jetkanto.domain.usecase.session.GetSessionUseCase
import com.ericktijerou.jetkanto.ui.entity.UserView
import com.ericktijerou.jetkanto.ui.entity.toView
import com.ericktijerou.jetkanto.ui.util.shareWhileObserved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    getSessionUseCase: GetSessionUseCase
) : ViewModel() {

    val session: SharedFlow<UserView> = getSessionUseCase.invoke()
        .distinctUntilChanged()
        .map { it.toView() }
        .flowOn(Dispatchers.IO)
        .shareWhileObserved(viewModelScope)
}
