package com.android.asc.amity.ui.screen.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import com.android.asc.amity.di.MainDispatcher
import com.android.asc.amity.di.ResourceModule
import com.android.asc.amity.ui.BaseViewModel
import com.android.asc.amity.ui.route.EventManager
import com.android.asc.amity.ui.route.NavigationBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
    private val eventManager: EventManager,
    private val stringResourcesProvider: ResourceModule.StringResourcesProvider,
) : BaseViewModel(dispatcher, eventManager, stringResourcesProvider) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.NavigateBack -> {
                navigateTo(NavigationBack)
            }
        }
    }
}
@Immutable
data class HomeUiState(
    val loading: Boolean = false
)
@Immutable
sealed interface HomeUiEvent {
    data object NavigateBack : HomeUiEvent
}