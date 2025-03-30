package pl.pelotasplus.openmeteo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import pl.pelotasplus.openmeteo.data.model.WeatherType
import pl.pelotasplus.openmeteo.feature.details.DetailsScreen
import pl.pelotasplus.openmeteo.feature.home.HomeScreen
import pl.pelotasplus.openmeteo.feature.splash.SplashScreen

sealed interface AppNavigationDestinations {
    @Serializable
    data object Splash : AppNavigationDestinations

    @Serializable
    data object Home : AppNavigationDestinations

    @Serializable
    data class Details(
        val location: String,
        val temperature: Double,
        val weatherType: WeatherType,
        val humidity: Double,
        val windSpeed: Double,
    ) : AppNavigationDestinations
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier.Companion.padding(paddingValues),
        navController = navController,
        startDestination = AppNavigationDestinations.Splash
    ) {
        composable<AppNavigationDestinations.Splash> {
            SplashScreen(
                permissionsGranted = {
                    navController.navigate(AppNavigationDestinations.Home) {
                        popUpTo<AppNavigationDestinations.Splash> { inclusive = true }
                    }
                }
            )
        }

        composable<AppNavigationDestinations.Home> {
            HomeScreen(
                goToDetails = {
                    navController.navigate(
                        AppNavigationDestinations.Details(
                            location = it.name,
                            temperature = it.currentWeather.temperature.toDouble(),
                            weatherType = it.currentWeather.type,
                            humidity = it.currentWeather.humidity,
                            windSpeed = it.currentWeather.windSpeed
                        )
                    )
                }
            )
        }

        composable<AppNavigationDestinations.Details> { backStackEntry ->
            DetailsScreen()
        }
    }
}
