package pl.pelotasplus.openmeteo.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named

const val WEATHER_API = "WEATHER_API"
const val GEOCODING_API = "GEOCODING_API"

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Named(WEATHER_API)
    fun provideWeatherBaseUrl(): String = "https://api.open-meteo.com/"


    @Provides
    @Named(GEOCODING_API)
    fun provideGeocodingBaseUrl(): String = "https://geocoding-api.open-meteo.com/"

    @Provides
    @Named(WEATHER_API)
    fun provideWeatherRetrofit(
        @Named(WEATHER_API) baseUrl: String, json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Named(GEOCODING_API)
    fun provideGeocodingRetrofit(
        @Named(GEOCODING_API) baseUrl: String, json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    fun provideWeatherApiService(@Named(WEATHER_API) retrofit: Retrofit): OpenMeteoApi =
        retrofit.create(OpenMeteoApi::class.java)

    @Provides
    fun provideGeocodingApiService(@Named(GEOCODING_API) retrofit: Retrofit): GeocodingApi =
        retrofit.create(GeocodingApi::class.java)
}
