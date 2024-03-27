package com.android.asc.amity.ui.route

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

sealed class Event

sealed class NavigationDataEvent(val destination: NavigationEvent, val dataEvent: DataEvent) : Event() {
    data class HomeDataEvent(val data: DataEvent.HomeData) : NavigationDataEvent(NavigationEvent.Home, data)
}

data object NavigationBack : Event()

sealed class NavigationEvent(val route: String) : Event() {
    data object Home : NavigationEvent(Routes.Home.route)
}

sealed class DataEvent : Event() {
    data class HomeData(val data: String) : DataEvent()
}

sealed class SnackbarEvent: Event() {
    data class StringSnackbar(val message: String) : SnackbarEvent()
    data class ResourceSnackbar(@StringRes val message: Int) : SnackbarEvent()
}

object EventManager {

    private val _eventShared = MutableSharedFlow<Event>(replay = 0)
    val eventShared: SharedFlow<Event> = _eventShared

    private val _dataShared = MutableSharedFlow<DataEvent>(replay = 1)
    val dataShared: SharedFlow<DataEvent> = _dataShared

    suspend fun sendEvent(event: Event) {
        when(event) {
            is NavigationEvent, is NavigationBack -> _eventShared.emit(manageDestination(event))
            is NavigationDataEvent -> {
                _eventShared.emit(event.destination)
                _dataShared.emit(event.dataEvent)
            }
            else -> _eventShared.emit(manageDestination(event))
        }
    }

    private fun manageDestination(event: Event): Event =
        event

}