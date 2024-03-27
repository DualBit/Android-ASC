package com.android.amity.sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.amity.sample.di.MainDispatcher
import com.android.amity.sample.di.ResourceModule
import com.android.amity.sample.ui.route.Event
import com.android.amity.sample.ui.route.EventManager
import com.android.amity.sample.ui.route.NavigationBack
import com.android.amity.sample.ui.route.SnackbarEvent
import com.android.amity.sample.util.loge
import com.android.asc.amity.R
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