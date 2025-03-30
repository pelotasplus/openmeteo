package pl.pelotasplus.openmeteo.domain.model

data class Weather(
    val currentWeather: CurrentWeather,
    val forecast: List<SingleDayForecast>
)
