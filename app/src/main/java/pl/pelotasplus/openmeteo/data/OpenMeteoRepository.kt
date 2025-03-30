package pl.pelotasplus.openmeteo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OpenMeteoRepository @Inject constructor(
    private val openMeteoApi: OpenMeteoApi
) {
    fun getForecast(): Flow<Unit> {
        return flow {
            val ret = openMeteoApi.forecast(
                latitude = 38.6447,
                longitude = 0.0445,
                daily = listOf("weather_code", "temperature_2m_max", "temperature_2m_min")
            )
            emit(Unit)
        }
    }
}
