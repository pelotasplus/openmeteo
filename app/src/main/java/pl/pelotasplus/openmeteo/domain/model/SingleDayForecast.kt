package pl.pelotasplus.openmeteo.domain.model

import pl.pelotasplus.openmeteo.data.model.WeatherType

data class SingleDayForecast(
    val date: String,
    val type: WeatherType,
    val temperatureMax: Double,
    val temperatureMin: Double
)
