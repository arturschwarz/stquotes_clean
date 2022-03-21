package dev.arturschwarz.stquotes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import dev.arturschwarz.stquotes.viewmodel.ScreenViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

@Composable
fun <T> rememberViewState(
    viewModel: ScreenViewModel<T, *>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> {
    return rememberFlowWithLifecycle(
        flow = viewModel.viewState,
        lifecycle = lifecycle,
        minActiveState = minActiveState
    ).collectAsState(viewModel.requireViewState())
}