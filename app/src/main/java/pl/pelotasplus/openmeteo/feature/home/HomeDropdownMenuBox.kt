package pl.pelotasplus.openmeteo.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pelotasplus.openmeteo.R
import pl.pelotasplus.openmeteo.data.model.WeatherType
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SearchResult
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDropdownMenuBox(
    state: HomeViewModel.State,
    modifier: Modifier = Modifier,
    onSearchTermChanged: (String) -> Unit = {},
    onSearchResultClicked: (SearchResult) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.searchResults) {
        expanded = state.searchResults.isNotEmpty()
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .menuAnchor(),
            value = state.searchTerm,
            onValueChange = {
                onSearchTermChanged(it)
            },
            trailingIcon = {
                IconButton(
                    modifier = modifier,
                    onClick = {
                        onSearchTermChanged("")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        stringResource(R.string.home_search_clear_content_description),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { }
        ) {
            state.searchResults.forEach {
                HomeSearchResultContent(
                    modifier = Modifier
                        .padding(ExposedDropdownMenuDefaults.ItemContentPadding)
                        .clickable {
                            onSearchResultClicked(it)
                        },
                    searchResult = it
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeDropdownMenuBoxPreview() {
    OpenMeteoTheme {
        HomeDropdownMenuBox(
            state = HomeViewModel.State(
                searchTerm = "Search term",
                searchResults = listOf(
                    SearchResult(
                        name = "London",
                        country = "UK",
                        latitude = 51.5085,
                        longitude = -0.1257,
                        currentWeather = CurrentWeather(
                            date = "2023-03-29",
                            type = WeatherType.ClearSky,
                            temperature = "10.0",
                            windSpeed = 20.0,
                            humidity = 30.0
                        )
                    )
                )
            )
        )
    }
}
