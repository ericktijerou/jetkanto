package com.ericktijerou.jetkanto.ui.util

sealed class ViewState {
    object Loading : ViewState()
    object Success : ViewState()
}