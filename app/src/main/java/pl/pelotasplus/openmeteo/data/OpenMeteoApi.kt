package pl.pelotasplus.openmeteo.data

import pl.pelotasplus.openmeteo.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {

    @GET("v1/forecast")
    suspend fun forecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: List<String>,
        @Query("current") current: List<String>
    ): ForecastResponse

}
