package dev.arturschwarz.stquotes.viewmodel

import androidx.lifecycle.viewModelScope
import dev.arturschwarz.stquotes.Result
import dev.arturschwarz.stquotes.State
import dev.arturschwarz.stquotes.data.Quote
import dev.arturschwarz.stquotes.data.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainScreenViewModel :
    ScreenViewModel<MainScreenViewModel.ViewState, MainScreenViewModel.ViewIntent>() {

    private val quotesRepository by inject<QuotesRepository>(QuotesRepository::class.java)

    data class ViewState(
        val state: State = State.Initial,
        val quotes: List<Quote> = listOf()
    )

    override fun sendViewIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.Refresh -> {
                updateViewState {
                    it.copy(state = State.Loading)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    when (val quotesResult = quotesRepository.getQuotes()) {
                        is Result.Success -> {
                            updateViewState {
                                it.copy(state = State.Success, quotes = quotesResult.data)
                            }
                        }
                        else -> {
                            updateViewState {
                                it.copy(state = State.Error)
                            }
                        }
                    }
                }
            }
        }
    }

    sealed class ViewIntent {
        object Refresh : ViewIntent()
    }

    override fun initViewState() = ViewState()

}