package pl.pelotasplus.openmeteo.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    fun handleEvent(event: Event) {
        when (event) {
            Event.GoToDetailsClicked -> {
                viewModelScope.launch {
                    _effect.send(Effect.ShowDetails)
                }
            }

            Event.LoadWeather -> {
                // todo
            }
        }
    }

    data class State(
        val location: String = "", // City, Country
        val loading: Boolean = true,
        val temperature: Float = 0f, // Celsius
        val condition: String = "", // Sunny, Rainy, Cloudy, etc.
        val weatherIcon: String = "", // URL to weather icon
    )

    sealed interface Event {
        data object LoadWeather : Event
        data object GoToDetailsClicked : Event
    }

    sealed interface Effect {
        data object Error : Effect
        data object ShowDetails : Effect
    }
}
