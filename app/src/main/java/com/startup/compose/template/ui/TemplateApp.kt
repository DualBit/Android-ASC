package com.startup.compose.template.ui

import android.content.res.Resources
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.startup.compose.template.manager.INetworkManager
import com.startup.compose.template.ui.route.EventManager
import com.startup.compose.template.ui.route.NavGraph


@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class,
)
@Composable
fun TemplateApp(
    eventManager: EventManager,
    networkMonitor: INetworkManager
) {
    val appState =
        rememberAppState(networkMonitor = networkMonitor, eventManager = eventManager)

    Scaffold(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxSize(),
//            .statusBarsPadding(),
        snackbarHost = {
            SnackbarHost(appState.snackbarHostState) { snackbarData: SnackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = Color.Red
                )
            }
        },
    ) { padding ->
        val isOffline by appState.isOffline.collectAsStateWithLifecycle()
        val notConnected = "⚠️ You aren’t connected to the internet"

        LaunchedEffect(isOffline) {
            if (isOffline) appState.snackbarHostState.showSnackbar(
                message = notConnected,
                duration = SnackbarDuration.Indefinite
            )
        }
        NavGraph(
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding),
            navController = appState.navController,
        )
    }

}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}