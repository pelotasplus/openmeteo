package pl.pelotasplus.openmeteo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import pl.pelotasplus.openmeteo.data.model.ForecastResponse
import pl.pelotasplus.openmeteo.data.model.convertWeatherCodeToType
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SingleDayForecast
import pl.pelotasplus.openmeteo.domain.model.Weather
import javax.inject.Inject

class OpenMeteoRepository @Inject constructor(
    private val openMeteoApi: OpenMeteoApi,
    private val json: Json
) {
    private suspend fun getRealForecast(lat: Double, lon: Double): ForecastResponse {
        return openMeteoApi.forecast(
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
    }

    private fun getMockedForecast(): ForecastResponse {
        val payload = """{
  "latitude": 38.6875,
  "longitude": 0.0625,
  "generationtime_ms": 0.0846385955810547,
  "utc_offset_seconds": 7200,
  "timezone": "Europe/Madrid",
  "timezone_abbreviation": "GMT+2",
  "elevation": 61,
  "current_units": {
    "time": "iso8601",
    "interval": "seconds",
    "temperature_2m": "°C",
    "weather_code": "wmo code",
    "wind_speed_10m": "km/h",
    "relative_humidity_2m": "%"
  },
  "current": {
    "time": "2025-03-30T15:00",
    "interval": 900,
    "temperature_2m": 17.7,
    "weather_code": 0,
    "wind_speed_10m": 11.6,
    "relative_humidity_2m": 41
  },
  "daily_units": {
    "time": "iso8601",
    "weather_code": "wmo code",
    "temperature_2m_max": "°C",
    "temperature_2m_min": "°C"
  },
  "daily": {
    "time": [
      "2025-03-30",
      "2025-03-31",
      "2025-04-01",
      "2025-04-02",
      "2025-04-03",
      "2025-04-04",
      "2025-04-05"
    ],
    "weather_code": [0, 0, 3, 61, 3, 3, 2],
    "temperature_2m_max": [17.8, 20.8, 19.8, 19.3, 18.6, 19.6, 22.9],
    "temperature_2m_min": [9.3, 10.3, 12, 13.5, 13.6, 15.1, 10.8]
  }
}""".trimIndent()
        val ret = json.decodeFromString<ForecastResponse>(payload)
        return ret
    }

    fun getForecast(lat: Double, lon: Double): Flow<Weather> {
        return flow {
            val ret = getRealForecast(lat, lon)
//            val ret = getMockedForecast()

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
