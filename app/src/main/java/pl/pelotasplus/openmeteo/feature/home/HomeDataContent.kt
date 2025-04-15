package pl.pelotasplus.openmeteo.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pelotasplus.openmeteo.R
import pl.pelotasplus.openmeteo.data.model.WeatherType
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SearchResult
import pl.pelotasplus.openmeteo.domain.model.SingleDayForecast
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeDataContent(
    state: HomeViewModel.State,
    modifier: Modifier = Modifier,
    onSearchTermChanged: (String) -> Unit = {},
    onSearchResultClicked: (SearchResult) -> Unit = {},
    onCurrentWeatherClicked: () -> Unit = {},
    onDeleteRecenSearch: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            state.recentSearches.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text(
                        text = "Search term: $it",
                        modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                    )

                    Button(
                        onClick = {
                            onDeleteRecenSearch(it)
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        }

        HomeDropdownMenuBox(
            state = state,
            onSearchTermChanged = onSearchTermChanged,
            onSearchResultClicked = onSearchResultClicked,
        )

        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clickable {
                    onCurrentWeatherClicked()
                },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = state.location,
                    style = MaterialTheme.typography.titleLarge
                )

                state.currentWeather?.let { currentWeather ->
                    Text(
                        text = stringResource(R.string.temperature_current, state.currentWeather.temperature),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = stringResource(currentWeather.type.stringRes),
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Image(
                        painter = painterResource(state.currentWeather.type.iconRes),
                        contentDescription = null,
                        modifier = Modifier.scale(2f)
                    )
                }
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(state.forecast) { item ->
                HomeForecastCard(item = item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeDataContentPreview() {
    OpenMeteoTheme {
        HomeDataContent(
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
                recentSearches = listOf("London", "Paris"),
                forecast = listOf(
                    SingleDayForecast(
                        date = "2023-03-29",
                        type = WeatherType.ClearSky,
                        temperatureMax = 10.0,
                        temperatureMin = 5.0
                    ),
                    SingleDayForecast(
                        date = "2023-03-30",
                        type = WeatherType.RainSlightModerateHeavy,
                        temperatureMax = 12.0,
                        temperatureMin = -5.0
                    )
                )
            )
        )
    }
}
