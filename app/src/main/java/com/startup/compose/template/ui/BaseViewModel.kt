package com.startup.compose.template.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.startup.compose.template.R
import com.startup.compose.template.di.MainDispatcher
import com.startup.compose.template.di.ResourceModule
import com.startup.compose.template.ui.route.Event
import com.startup.compose.template.ui.route.EventManager
import com.startup.compose.template.ui.route.NavigationBack
import com.startup.compose.template.ui.route.SnackbarEvent
import com.startup.compose.template.util.loge
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
    private val eventManager: EventManager,
    private val stringResourcesProvider: ResourceModule.StringResourcesProvider,
) : ViewModel() {

    open fun sendMessage(event: SnackbarEvent) {
        viewModelScope.launch {
            try {
                eventManager.sendEvent(event)
            } catch (e: Exception) {
                val errorMsg = e.message ?: stringResourcesProvider.getString(R.string.generic_error)
                loge(errorMsg)
            }
        }
    }

    open fun navigateTo(event: Event){
        viewModelScope.launch {
            try {
                eventManager.sendEvent(event)
            } catch (e: Exception) {
                val errorMsg =
                    e.message ?: stringResourcesProvider.getString(R.string.generic_error)
                sendMessage(SnackbarEvent.StringSnackbar(errorMsg))
                loge(errorMsg, e)
            }
        }
    }

    open fun onBackClick() {
        viewModelScope.launch(dispatcher) {
            eventManager.sendEvent(NavigationBack)
        }
    }
}