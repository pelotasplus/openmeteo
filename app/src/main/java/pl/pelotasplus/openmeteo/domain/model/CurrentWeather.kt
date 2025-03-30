package pl.pelotasplus.openmeteo.domain.model

import kotlinx.serialization.Serializable
import pl.pelotasplus.openmeteo.data.model.WeatherType

@Serializable
data class CurrentWeather(
    val date: String,
    val type: WeatherType,
    val temperature: String,
    val windSpeed: Double,
    val humidity: Double,
)
