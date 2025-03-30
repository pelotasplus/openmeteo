package pl.pelotasplus.openmeteo.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pelotasplus.openmeteo.data.model.WeatherType
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SingleDayForecast
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@Composable
internal fun HomeDataContent(
    state: HomeViewModel.State,
    modifier: Modifier = Modifier,
    onGoToDetailsClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = state.location,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = state.currentWeather?.temperature.orEmpty(),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = state.currentWeather?.let { stringResource(it.type.stringRes) } ?: "",
                    style = MaterialTheme.typography.titleLarge
                )

                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                )
            }
        }

        Card {
            LazyRow {
                items(state.forecast) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp).width(100.dp)
                    ) {
                        Text(
                            text = it.date,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                        )

                        Text(
                            text = stringResource(it.type.stringRes),
                            style = MaterialTheme.typography.titleSmall
                        )

                        Row {
                            Text(
                                text = "Min: ${it.temperatureMin}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = "Max: ${it.temperatureMax}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
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
