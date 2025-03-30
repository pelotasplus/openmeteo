package pl.pelotasplus.openmeteo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.pelotasplus.openmeteo.data.model.convertWeatherCodeToType
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SearchResult
import pl.pelotasplus.openmeteo.domain.model.SingleDayForecast
import pl.pelotasplus.openmeteo.domain.model.Weather
import javax.inject.Inject

class OpenMeteoRepository @Inject constructor(
    private val openMeteoApi: OpenMeteoApi,
    private val geocodingApi: GeocodingApi,
) {
    fun searchLocation(name: String): Flow<List<SearchResult>> {
        return flow {
            val ret = geocodingApi.search(
                name = name,
                count = 5,
                language = "en",
                format = "json"
            )

            val mappedResults = ret.results.map {
                SearchResult(
                    name = it.name,
                    country = it.country,
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }

            emit(mappedResults)
        }
    }

    fun getForecast(lat: Double, lon: Double): Flow<Weather> {
        return flow {
            val ret = openMeteoApi.forecast(
                latitude = lat,
                longitude = lon,
                daily = listOf("weather_code", "temperature_2m_max", "temperature_2m_min"),
                current = listOf(
                    "temperature_2m",
                    "weather_code",
                    "wind_speed_10m",
                    "relative_humidity_2m"
                )
            )

            val mappedForecasts = (0 until ret.daily.time.size).map {
                val time = ret.daily.time[it]
                val weatherCode = ret.daily.weatherCode[it]
                val temperatureMax = ret.daily.temperature2mMax[it]
                val temperatureMin = ret.daily.temperature2mMin[it]

                SingleDayForecast(
                    date = time,
                    type = convertWeatherCodeToType(weatherCode),
                    temperatureMax = temperatureMax,
                    temperatureMin = temperatureMin
                )
            }.subList(1, 6)

            val currentWeather = CurrentWeather(
                date = ret.current.time,
                type = convertWeatherCodeToType(ret.current.weatherCode),
                temperature = ret.current.temperature2m.toString(),
                windSpeed = ret.current.windSpeed,
                humidity = ret.current.humidity
            )

            emit(Weather(currentWeather, mappedForecasts))
        }
    }
}
