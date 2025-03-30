package pl.pelotasplus.openmeteo.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepository @Inject constructor(@ApplicationContext context: Context) {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val geocoder = Geocoder(context)

    @SuppressLint("MissingPermission")
    fun getLastLocation(): Flow<Location> = flow {
        emit(fusedLocationProviderClient.lastLocation.asDeferred().await())
    }

    fun getLocationAddress(latitude: Double, longitude: Double) = flow {
        emit(getAddress(latitude, longitude))
    }

    private suspend fun getAddress(
        latitude: Double,
        longitude: Double,
    ): Address? = withContext(Dispatchers.IO) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCoroutine { cont ->
                    geocoder.getFromLocation(latitude, longitude, 1) {
                        cont.resume(it.firstOrNull())
                    }
                }
            } else {
                suspendCoroutine { cont ->
                    @Suppress("DEPRECATION")
                    val address = geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()
                    cont.resume(address)
                }
            }
        } catch (e: Exception) {
            ensureActive()
            null
        }
    }
}
