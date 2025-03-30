package pl.pelotasplus.openmeteo.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import pl.pelotasplus.openmeteo.AppNavigationDestinations
import pl.pelotasplus.openmeteo.data.model.WeatherType
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleEvent(Event.LoadInitialState(savedStateHandle.toRoute<AppNavigationDestinations.Details>()))
    }

    fun handleEvent(event: Event) {
        when (event) {
            is Event.LoadInitialState -> {
                _state.update {
                    it.copy(
                        loading = false,
                        location = event.details.location,
                        temperature = event.details.temperature,
                        weatherType = event.details.weatherType,
                        humidity = event.details.humidity,
                        windSpeed = event.details.windSpeed
                    )
                }
            }
        }
    }

    data class State(
        val loading: Boolean = true,
        val location: String = "",
        val temperature: Double = 0.0,
        val weatherType: WeatherType = WeatherType.ClearSky,
        val humidity: Double = 0.0,
        val windSpeed: Double = 0.0,
    )

    sealed interface Effect

    sealed interface Event {
        data class LoadInitialState(val details: AppNavigationDestinations.Details) : Event
    }
}
