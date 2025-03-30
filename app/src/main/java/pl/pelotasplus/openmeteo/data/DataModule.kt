package pl.pelotasplus.openmeteo.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideBaseUrl(): String = "https://api.open-meteo.com/"

    @Provides
    fun provideRetrofit(baseUrl: String, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): OpenMeteoApi =
        retrofit.create(OpenMeteoApi::class.java)
}
