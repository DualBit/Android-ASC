package com.startup.compose.template.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Destinations used in the [SportLifeAppState].
 */
sealed class Routes(val route: String) {
    data object Home : Routes("home")
}


/**
 * Models the navigation actions in the app.
 * [popUpTo] Pop up to the start destination of the graph to avoid building up a large stack of
 * destinations on the back stack as users select items.
 * [inclusive] remove previous Composable from back stack.
 * [launchSingleTop] Avoid multiple copies of the same destination when re-selecting the same item.
 * [restoreState] estore state when re-selecting a previously selected item
 */
class NavigationManager(private val navController: NavHostController) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val currentRoute: String
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route ?: Routes.Home.route

    val navigateTo: (route: String) -> Unit = {
        navController.navigate(it) {
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToBottomTab: (route: String) -> Unit = {
        navController.navigate(it) {
            popUpTo(Routes.Home.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val onBack: () -> Unit = {
        navController.popBackStack()
    }

}