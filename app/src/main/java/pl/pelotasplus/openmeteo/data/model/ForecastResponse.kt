package pl.pelotasplus.openmeteo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("generationtime_ms") val generationTimeMs: Double,
    @SerialName("utc_offset_seconds") val utcOffsetSeconds: Int,
    @SerialName("timezone") val timezone: String,
    @SerialName("timezone_abbreviation") val timezoneAbbreviation: String,
    @SerialName("elevation") val elevation: Double,
    @SerialName("daily_units") val dailyUnits: DailyUnits,
    @SerialName("daily") val daily: Daily,
    @SerialName("current") val current: Current
)

@Serializable
data class Current(
    @SerialName("time") val time: String,
    @SerialName("temperature_2m") val temperature2m: Double,
    @SerialName("weather_code") val weatherCode: Int,
    @SerialName("wind_speed_10m") val windSpeed: Double,
    @SerialName("relative_humidity_2m") val humidity: Double,
)

@Serializable
data class DailyUnits(
    @SerialName("time") val time: String,
    @SerialName("weather_code") val weatherCode: String,
    @SerialName("temperature_2m_max") val temperature2mMax: String,
    @SerialName("temperature_2m_min") val temperature2mMin: String
)

@Serializable
data class Daily(
    @SerialName("time") val time: List<String>,
    @SerialName("weather_code") val weatherCode: List<Int>,
    @SerialName("temperature_2m_max") val temperature2mMax: List<Double>,
    @SerialName("temperature_2m_min") val temperature2mMin: List<Double>
)
