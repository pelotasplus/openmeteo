package pl.pelotasplus.openmeteo.domain.model

data class SearchResult(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
)
