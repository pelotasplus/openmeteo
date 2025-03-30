package pl.pelotasplus.openmeteo.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.pelotasplus.openmeteo.R
import pl.pelotasplus.openmeteo.core.ObserveEffects
import pl.pelotasplus.openmeteo.data.model.WeatherType

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEffects(viewModel.effect) { effect ->
        // no-op
    }

    DetailsContent(
        modifier = modifier,
        state = state
    )
}

@Composable
private fun DetailsContent(
    modifier: Modifier = Modifier,
    state: DetailsViewModel.State,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.location,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = stringResource(R.string.temperature_current, state.temperature),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = stringResource(state.weatherType.stringRes),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Image(
            painter = painterResource(state.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.scale(4f).padding(32.dp)
        )

        Text(
            text = stringResource(R.string.wind_speed, state.windSpeed),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.humidity, state.humidity),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsContentPreview() {
    DetailsContent(
        state = DetailsViewModel.State(
            loading = false,
            location = "London, UK",
            temperature = 10.0,
            weatherType = WeatherType.ClearSky,
            humidity = 30.0,
            windSpeed = 24.0
        )
    )
}
