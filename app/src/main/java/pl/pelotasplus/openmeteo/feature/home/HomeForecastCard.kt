package pl.pelotasplus.openmeteo.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
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
import pl.pelotasplus.openmeteo.domain.model.SingleDayForecast
import pl.pelotasplus.openmeteo.ui.theme.OpenMeteoTheme

@Composable
fun HomeForecastCard(
    item: SingleDayForecast,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .width(150.dp)
                .height(140.dp)
        ) {
            Text(
                text = item.date,
                style = MaterialTheme.typography.titleSmall,
            )

            Image(
                painter = painterResource(item.type.iconRes),
                contentDescription = null,
                modifier = Modifier
                    .scale(2f)
                    .padding(vertical = 8.dp)
            )

            Text(
                text = stringResource(item.type.stringRes),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(1f))

            Row {
                Text(
                    text = stringResource(R.string.temperature_min, item.temperatureMin),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.temperature_max, item.temperatureMax),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeForecastCardPreview() {
    OpenMeteoTheme {
        HomeForecastCard(
            item = SingleDayForecast(
                date = "2023-03-29",
                type = WeatherType.ClearSky,
                temperatureMax = 10.0,
                temperatureMin = 5.0
            )
        )
    }
}
