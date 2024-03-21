package com.android.amity.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.android.amity.sample.manager.INetworkManager
import com.android.amity.sample.ui.AndroidAmityApp
import com.android.amity.sample.ui.route.EventManager
import com.android.amity.sample.ui.theme.AndroidAmityTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var eventManager: EventManager
    @Inject
    lateinit var networkMonitor: INetworkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = isSystemInDarkTheme()

            // Update the dark content of the system bars to match the theme
            DisposableEffect(systemUiController, darkTheme) {
                //systemUiController.setSystemBarsColor(light_primary, darkTheme)
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }
            ProvideWindowInsets {
                AndroidAmityTheme {
                    AndroidAmityApp(eventManager, networkMonitor)
                }
            }

        }
    }
}
