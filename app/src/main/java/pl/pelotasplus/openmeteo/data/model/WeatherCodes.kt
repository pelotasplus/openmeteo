package pl.pelotasplus.openmeteo.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import pl.pelotasplus.openmeteo.R

enum class WeatherType(@StringRes val stringRes: Int, @DrawableRes val iconRes: Int) {
    ClearSky(R.string.weather_clear_sky, R.drawable.wi_0d_big),
    MainlyClearPartlyCloudyOvercast(
        R.string.weather_mainly_clear_partly_cloudy_overcast,
        R.drawable.wi_1d_big
    ),
    FogAndDepositingRimeFog(R.string.weather_fog_depositing_rime_fog, R.drawable.wi_45d_big),
    DrizzleLightModerateDense(R.string.weather_drizzle_light_moderate_dense, R.drawable.wi_51d_big),
    FreezingDrizzleLightDense(R.string.weather_freezing_drizzle_light_dense, R.drawable.wi_56d_big),
    RainSlightModerateHeavy(R.string.weather_rain_slight_moderate_heavy, R.drawable.wi_61d_big),
    FreezingRainLightHeavy(R.string.weather_freezing_rain_light_heavy, R.drawable.wi_66d_big),
    SnowFallSlightModerateHeavy(
        R.string.weather_snow_fall_slight_moderate_heavy,
        R.drawable.wi_71d_big
    ),
    SnowGrains(R.string.weather_snow_grains, R.drawable.wi_77d_big),
    RainShowersSlightModerateViolent(
        R.string.weather_rain_showers_slight_moderate_violent,
        R.drawable.wi_80d_big
    ),
    SnowShowersSlightHeavy(R.string.weather_snow_showers_slight_heavy, R.drawable.wi_85d_big),
    ThunderstormSlightModerate(
        R.string.weather_thunderstorm_slight_moderate,
        R.drawable.wi_95d_big
    ),
    ThunderstormHailSlightHeavy(
        R.string.weather_thunderstorm_hail_slight_heavy,
        R.drawable.wi_96d_big
    )
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
