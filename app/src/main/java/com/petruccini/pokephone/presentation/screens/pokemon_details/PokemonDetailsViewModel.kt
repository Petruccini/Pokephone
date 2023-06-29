package com.petruccini.pokephone.presentation.screens.pokemon_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petruccini.pokephone.domain.entities.PokemonDetails
import com.petruccini.pokephone.domain.use_cases.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonDetailsUiState(
    val pokemonDetails: PokemonDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun getPokemonDetails(pokemonName: String) {
        if (_uiState.value.isLoading) return
        _uiState.update { it.copy(isLoading = true) }

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getPokemonDetailsUseCase(pokemonName)
                .catch { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message)
                    }
                }
                .collect { pokemonDetails ->
                    if (pokemonDetails != null) {
                        _uiState.update {
                            it.copy(pokemonDetails = pokemonDetails, isLoading = false, error = null)
                        }
                    } else {
                        // TODO Implement a better error handling
                        _uiState.update {
                            it.copy(isLoading = false, error = "Error loading pokemon details")
                        }
                    }
                }
        }
    }
}