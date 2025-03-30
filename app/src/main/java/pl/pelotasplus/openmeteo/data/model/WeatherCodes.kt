package pl.pelotasplus.openmeteo.data.model

import androidx.annotation.StringRes
import pl.pelotasplus.openmeteo.R

enum class WeatherType(@StringRes val stringRes: Int) {
    ClearSky(R.string.weather_clear_sky),
    MainlyClearPartlyCloudyOvercast(R.string.weather_mainly_clear_partly_cloudy_overcast),
    FogAndDepositingRimeFog(R.string.weather_fog_depositing_rime_fog),
    DrizzleLightModerateDense(R.string.weather_drizzle_light_moderate_dense),
    FreezingDrizzleLightDense(R.string.weather_freezing_drizzle_light_dense),
    RainSlightModerateHeavy(R.string.weather_rain_slight_moderate_heavy),
    FreezingRainLightHeavy(R.string.weather_freezing_rain_light_heavy),
    SnowFallSlightModerateHeavy(R.string.weather_snow_fall_slight_moderate_heavy),
    SnowGrains(R.string.weather_snow_grains),
    RainShowersSlightModerateViolent(R.string.weather_rain_showers_slight_moderate_violent),
    SnowShowersSlightHeavy(R.string.weather_snow_showers_slight_heavy),
    ThunderstormSlightModerate(R.string.weather_thunderstorm_slight_moderate),
    ThunderstormHailSlightHeavy(R.string.weather_thunderstorm_hail_slight_heavy)
}

fun convertWeatherCodeToType(weatherCode: Int): WeatherType {
    return when (weatherCode) {
        0 -> WeatherType.ClearSky
        1, 2, 3 -> WeatherType.MainlyClearPartlyCloudyOvercast
        45, 48 -> WeatherType.FogAndDepositingRimeFog
        51, 53, 55 -> WeatherType.DrizzleLightModerateDense
        56, 57 -> WeatherType.FreezingDrizzleLightDense
        61, 63, 65 -> WeatherType.RainSlightModerateHeavy
        66, 67 -> WeatherType.FreezingRainLightHeavy
        71, 73, 75 -> WeatherType.SnowFallSlightModerateHeavy
        77 -> WeatherType.SnowGrains
        80, 81, 82 -> WeatherType.RainShowersSlightModerateViolent
        85, 86 -> WeatherType.SnowShowersSlightHeavy
        95 -> WeatherType.ThunderstormSlightModerate
        96, 99 -> WeatherType.ThunderstormHailSlightHeavy
        else -> throw IllegalArgumentException("Unknown weather code: $weatherCode")
    }
}
