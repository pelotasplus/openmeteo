package pl.pelotasplus.openmeteo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val currentWeather: CurrentWeather
)
