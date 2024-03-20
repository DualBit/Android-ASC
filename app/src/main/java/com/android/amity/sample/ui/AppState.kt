package com.android.amity.sample.ui

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.amity.sample.manager.INetworkManager
import com.android.amity.sample.ui.route.EventManager
import com.android.amity.sample.ui.route.NavigationBack
import com.android.amity.sample.ui.route.NavigationEvent
import com.android.amity.sample.ui.route.NavigationManager
import com.android.amity.sample.ui.route.SnackbarEvent
import com.android.amity.sample.util.loge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    networkMonitor: INetworkManager,
    eventManager: EventManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navigationManager: NavigationManager = NavigationManager(navController),
    resources: Resources = resources(),
): AppState {
    return remember(
        networkMonitor,
        eventManager,
        coroutineScope,
        navController,
        snackbarHostState,
        resources
    ) {
        AppState(
            navController,
            coroutineScope,
            snackbarHostState,
            navigationManager,
            resources,
            eventManager,
            networkMonitor
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
    private val navigationManager: NavigationManager,
    private val resources: Resources,
    eventManager: EventManager,
    networkMonitor: INetworkManager,
) {
    init {
        coroutineScope.launch {
            try {
                eventManager.eventShared.collectLatest {
                    when (it) {
                        is NavigationEvent -> navigationManager.navigateTo(it.route)
                        is NavigationBack -> navigationManager.onBack()
                        is SnackbarEvent.ResourceSnackbar -> snackbarHostState.showSnackbar(resources.getString(it.message))
                        is SnackbarEvent.StringSnackbar -> snackbarHostState.showSnackbar(it.message)
                        else -> {}
                    }
                }
            } catch (e: Exception) {
                loge(e.message ?: "", e)
            }
        }
    }

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
}