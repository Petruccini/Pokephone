package com.petruccini.pokephone.presentation.screens.pokemon_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petruccini.pokephone.domain.entities.PokemonDetails
import com.petruccini.pokephone.domain.use_cases.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase
) : ViewModel() {

    private val _pokemonDetailsStateFlow = MutableStateFlow<PokemonDetails?>(null)
    val pokemonDetailsStateFlow = _pokemonDetailsStateFlow

    private val _loadingPokemonDetailsStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingPokemonDetailsStateFlow = _loadingPokemonDetailsStateFlow.asStateFlow()

    private val _errorStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorStateFlow = _errorStateFlow.asStateFlow()

    fun getPokemonDetails(pokemonName: String) {
        if (loadingPokemonDetailsStateFlow.value) return

        viewModelScope.launch {
            getPokemonDetailsUseCase(pokemonName)
                .catch {
                    _loadingPokemonDetailsStateFlow.value = false
                    _errorStateFlow.value = it.message
                }
                .collect {
                _pokemonDetailsStateFlow.value = it
                _loadingPokemonDetailsStateFlow.value = false
            }
        }
    }
}