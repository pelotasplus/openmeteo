package pl.pelotasplus.openmeteo.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pelotasplus.openmeteo.data.LocationRepository
import pl.pelotasplus.openmeteo.data.OpenMeteoRepository
import pl.pelotasplus.openmeteo.domain.model.CurrentWeather
import pl.pelotasplus.openmeteo.domain.model.SearchResult
import pl.pelotasplus.openmeteo.domain.model.SingleDayForecast
import pl.pelotasplus.openmeteo.feature.home.HomeViewModel.Effect.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val openMeteoRepository: OpenMeteoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleEvent(Event.LoadWeather)
        observeSearchTerm()
    }

    fun handleEvent(event: Event) {
        when (event) {
            is Event.SearchResultClicked -> {
                viewModelScope.launch {
                    _effect.send(ShowDetails(event.searchResult))
                }
            }

            Event.LoadWeather -> {
                handleLoadWeather()
            }

            is Event.SearchTermChanged -> {
                _state.update {
                    it.copy(searchTerm = event.term)
                }
            }

            Event.CurrentWeatherClicked -> {
                state.value.currentWeather?.let {
                    viewModelScope.launch {
                        _effect.send(
                            ShowDetails(
                                SearchResult(
                                    name = state.value.location,
                                    country = "",
                                    latitude = 0.0,
                                    longitude = 0.0,
                                    currentWeather = it
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeSearchTerm() {
        _state
            .map { it.searchTerm }
            .distinctUntilChanged()
            .debounce(500.milliseconds)
            .flatMapLatest {
                if (it.isBlank()) {
                    flowOf(emptyList())
                } else {
                    openMeteoRepository.searchLocation(it)
                        .catch { emit(emptyList()) }
                }
            }
            .onEach { searchResults ->
                _state.update {
                    it.copy(
                        searchResults = searchResults
                    )
                }
            }
            .catch {
                handleError(it)
            }
            .launchIn(viewModelScope)
    }

    private fun handleLoadWeather() {
        locationRepository.getLastLocation()
            .take(1)
            .flatMapLatest { location ->
                combine(
                    openMeteoRepository.getForecast(
                        lat = location.latitude,
                        lon = location.longitude
                    ),
                    locationRepository.getLocationAddress(
                        location.latitude,
                        location.longitude
                    )
                ) { weather, address ->
                    println("XXX got weather $weather")
                    _state.update {
                        it.copy(
                            location = address?.let {
                                it.locality + ", " + it.countryName
                            } ?: "Unknown location",
                            forecast = weather.forecast,
                            currentWeather = weather.currentWeather
                        )
                    }
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

    private fun handleError(error: Throwable) {
        viewModelScope.launch {
            _effect.send(Error(error))
        }
    }

    data class State(
        val searchTerm: String = "",
        val location: String = "Not set yet",
        val searchResults: List<SearchResult> = emptyList(),
        val loading: Boolean = true,
        val forecast: List<SingleDayForecast> = emptyList(),
        val currentWeather: CurrentWeather? = null
    )

    sealed interface Event {
        data object LoadWeather : Event
        data object CurrentWeatherClicked : Event
        data class SearchResultClicked(val searchResult: SearchResult) : Event
        data class SearchTermChanged(val term: String) : Event
    }

    sealed interface Effect {
        data class Error(val error: Throwable) : Effect
        data class ShowDetails(val searchResult: SearchResult) : Effect
    }
}
