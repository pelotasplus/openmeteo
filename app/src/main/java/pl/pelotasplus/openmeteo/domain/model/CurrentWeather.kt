package pl.pelotasplus.openmeteo.domain.model

import pl.pelotasplus.openmeteo.data.model.WeatherType

data class CurrentWeather(
    val date: String,
    val type: WeatherType,
    val temperature: String,
    val windSpeed: Double,
    val humidity: Double,
)
