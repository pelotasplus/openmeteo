package pl.pelotasplus.openmeteo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import pl.pelotasplus.openmeteo.feature.details.DetailsScreen
import pl.pelotasplus.openmeteo.feature.home.HomeScreen

sealed interface AppNavigationDestinations {
    @Serializable
    data object Home : AppNavigationDestinations

    @Serializable
    data object Details : AppNavigationDestinations
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier.Companion.padding(paddingValues),
        navController = navController,
        startDestination = AppNavigationDestinations.Home
    ) {
        composable<AppNavigationDestinations.Home> {
            HomeScreen(
                goToDetails = {
                    navController.navigate(AppNavigationDestinations.Details)
                }
            )
        }

        composable<AppNavigationDestinations.Details> {
            DetailsScreen()
        }
    }
}
