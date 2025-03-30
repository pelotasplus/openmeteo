package pl.pelotasplus.openmeteo.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pelotasplus.openmeteo.R
import pl.pelotasplus.openmeteo.data.model.WeatherType
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SearchResult
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@Composable
fun HomeSearchResultContent(
    modifier: Modifier = Modifier,
    searchResult: SearchResult
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = searchResult.name + ", " + searchResult.country,
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(stringResource(R.string.temperature_min, searchResult.currentWeather.temperature))
            Text(stringResource(R.string.temperature_max, searchResult.currentWeather.temperature))
        }
        Text(stringResource(searchResult.currentWeather.type.stringRes))
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSearchResultContentPreview() {
    OpenMeteoTheme {
        HomeSearchResultContent(
            searchResult = SearchResult(
                name = "London",
                country = "UK",
                latitude = 1.0,
                longitude = 2.0,
                currentWeather = CurrentWeather(
                    date = "2023-03-29",
                    type = WeatherType.ClearSky,
                    temperature = "10.0",
                    windSpeed = 20.0,
                    humidity = 30.0
                )
            )
        )
    }
}
