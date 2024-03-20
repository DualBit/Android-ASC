package com.android.amity.sample.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.amity.sample.ui.screen.home.HomeScreen

@ExperimentalComposeUiApi
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen()
        }

    }
}
