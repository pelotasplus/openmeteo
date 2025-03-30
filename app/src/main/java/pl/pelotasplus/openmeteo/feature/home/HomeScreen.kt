package pl.pelotasplus.openmeteo.feature.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.pelotasplus.openmeteo.core.ObserveEffects
import pl.pelotasplus.openmeteo.data.model.WeatherType
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SearchResult
import pl.pelotasplus.openmeteo.domain.model.SingleDayForecast
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    goToDetails: () -> Unit
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEffects(viewModel.effect) { effect ->
        when (effect) {
            is HomeViewModel.Effect.Error -> {
                Toast.makeText(context, effect.error.message, Toast.LENGTH_SHORT).show()
            }

            HomeViewModel.Effect.ShowDetails -> {
                goToDetails()
            }
        }
    }

    HomeContent(
        modifier = modifier,
        state = state,
        onSearchTermChanged = { term ->
            viewModel.handleEvent(HomeViewModel.Event.SearchTermChanged(term))
        },
        onSearchResultClicked = {
            viewModel.handleEvent(HomeViewModel.Event.SearchResultClicked(it))
        }
    )
}

@Composable
private fun HomeContent(
    state: HomeViewModel.State,
    modifier: Modifier = Modifier,
    onSearchTermChanged: (String) -> Unit = {},
    onSearchResultClicked: (SearchResult) -> Unit = {}
) {
    if (state.loading) {
        HomeLoadingContent(modifier)
    } else {
        HomeDataContent(
            state,
            modifier,
            onSearchTermChanged,
            onSearchResultClicked
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentHasDataPreview() {
    OpenMeteoTheme {
        HomeContent(
            state = HomeViewModel.State(
                loading = false,
                location = "London, UK",
                currentWeather = CurrentWeather(
                    date = "2023-03-29",
                    type = WeatherType.ClearSky,
                    temperature = "10.0",
                    windSpeed = 20.0,
                    humidity = 30.0
                ),
                forecast = listOf(
                    SingleDayForecast(
                        date = "2023-03-29",
                        type = WeatherType.ClearSky,
                        temperatureMax = 10.0,
                        temperatureMin = 5.0
                    )
                )
            )
        )
    }
}
