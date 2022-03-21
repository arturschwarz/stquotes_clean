package dev.arturschwarz.stquotes.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ScreenViewModel<ViewState, ViewIntent> : ViewModel() {
    val viewState: StateFlow<ViewState> by ::_viewState

    private val _viewState: MutableStateFlow<ViewState> by lazy { MutableStateFlow(initViewState()) }

    fun requireViewState() = viewState.value!!

    protected fun updateViewState(block: (ViewState) -> ViewState) {
        _viewState.value = block(_viewState.value!!)
    }

    abstract fun initViewState(): ViewState

    abstract fun sendViewIntent(intent: ViewIntent)
}