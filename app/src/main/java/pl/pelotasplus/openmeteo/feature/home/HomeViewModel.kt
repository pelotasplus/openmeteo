package pl.pelotasplus.openmeteo.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pelotasplus.openmeteo.data.OpenMeteoRepository
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SingleDayForecast
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val openMeteoRepository: OpenMeteoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleEvent(Event.LoadWeather)
    }

    fun handleEvent(event: Event) {
        when (event) {
            Event.GoToDetailsClicked -> {
                viewModelScope.launch {
                    _effect.send(Effect.ShowDetails)
                }
            }

            Event.LoadWeather -> {
                openMeteoRepository.getForecast()
                    .onEach { weather ->
                        _state.update {
                            it.copy(
                                forecast = weather.forecast,
                                currentWeather = weather.currentWeather
                            )
                        }
                    }
                    .onCompletion {
                        _state.update {
                            it.copy(loading = false)
                        }
                    }
                    .catch {
                        handleError(it)
                    }
                    .launchIn(viewModelScope)
            }
        }
    }

    private fun handleError(error: Throwable) {
        viewModelScope.launch {
            _effect.send(Effect.Error(error))
        }
    }

    data class State(
        val location: String = "Not set yet",
        val loading: Boolean = true,
        val forecast: List<SingleDayForecast> = emptyList(),
        val currentWeather: CurrentWeather? = null
    )

    sealed interface Event {
        data object LoadWeather : Event
        data object GoToDetailsClicked : Event
    }

    sealed interface Effect {
        data class Error(val error: Throwable) : Effect
        data object ShowDetails : Effect
    }
}
