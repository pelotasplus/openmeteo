package pl.pelotasplus.openmeteo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponse(
    @SerialName("results") val results: List<GeocodingResult>,
)

@Serializable
data class GeocodingResult(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("elevation") val elevation: Double,
    @SerialName("feature_code") val featureCode: String,
    @SerialName("country_code") val countryCode: String,
    @SerialName("admin1_id") val admin1Id: Int? = null,
    @SerialName("timezone") val timezone: String,
    @SerialName("country_id") val countryId: Int,
    @SerialName("country") val country: String,
    @SerialName("admin1") val admin1: String? = null,
    @SerialName("admin2_id") val admin2Id: Int? = null,
    @SerialName("admin3_id") val admin3Id: Int? = null,
    @SerialName("admin4_id") val admin4Id: Int? = null,
    @SerialName("population") val population: Int? = null,
    @SerialName("postcodes") val postcodes: List<String>? = null,
    @SerialName("admin2") val admin2: String? = null,
    @SerialName("admin3") val admin3: String? = null,
    @SerialName("admin4") val admin4: String? = null
)
