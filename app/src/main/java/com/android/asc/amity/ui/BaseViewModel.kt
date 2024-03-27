package com.android.asc.amity.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.asc.amity.di.MainDispatcher
import com.android.asc.amity.di.ResourceModule
import com.android.asc.amity.ui.route.EventManager
import com.android.asc.amity.ui.route.NavigationBack
import com.android.asc.amity.ui.route.SnackbarEvent
import com.android.asc.amity.util.loge
import com.android.asc.amity.R
import com.android.asc.amity.ui.route.Event
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