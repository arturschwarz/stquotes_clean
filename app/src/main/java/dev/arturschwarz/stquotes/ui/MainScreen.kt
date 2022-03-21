package dev.arturschwarz.stquotes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import dev.arturschwarz.stquotes.R
import dev.arturschwarz.stquotes.State
import dev.arturschwarz.stquotes.data.Quote
import dev.arturschwarz.stquotes.rememberViewState
import dev.arturschwarz.stquotes.viewmodel.MainScreenViewModel

@Composable
fun MainScreen() {
    val viewModel = MainScreenViewModel()
    val viewState = rememberViewState(viewModel = viewModel)

    Scaffold(topBar = {
        AppBar(onAppBarItemSelected = {
            viewModel.sendViewIntent(MainScreenViewModel.ViewIntent.Refresh)
        })
    }) {
        when (viewState.value.state) {
            State.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            State.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(viewState.value.quotes) { quote ->
                        QuoteItem(quote = quote)
                    }
                }
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.no_quotes_available)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendViewIntent(MainScreenViewModel.ViewIntent.Refresh)
    }
}

@Composable
private fun QuoteItem(quote: Quote) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.quote, quote.quote),
                fontStyle = FontStyle.Italic
            )
            Text(text = stringResource(R.string.author, quote.author))
        }
    }
}

