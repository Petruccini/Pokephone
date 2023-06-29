package com.petruccini.pokephone.presentation.screens.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.use_cases.pokemon_list.GetPokemonListUseCase
import com.petruccini.pokephone.domain.use_cases.pokemon_list.POKEMON_LIST_LIMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonListUiState(
    val pokemonList: List<PokemonItem> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState = _uiState.asStateFlow()

    private val listOfPokemon = mutableListOf<PokemonItem>()
    private var loadedAllPokemons: Boolean = false

    private var fetchJob: Job? = null

    fun loadMorePokemons() {
        if (_uiState.value.isLoading) return
        if (loadedAllPokemons) return

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val currentPage = _uiState.value.pokemonList.size / POKEMON_LIST_LIMIT
            getNewPokemonsPage(currentPage)
        }
    }

    private suspend fun getNewPokemonsPage(
        currentPage: Int
    ) {
        _uiState.update { it.copy(isLoading = true) }
        getPokemonListUseCase(currentPage)
            .catch { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
            .collect {pokemonList ->
                if (pokemonList != null) {
                    listOfPokemon.addAll(pokemonList.pokemonItems)
                    if (listOfPokemon.size == pokemonList.count) loadedAllPokemons = true
                    _uiState.update { it.copy(isLoading = false, pokemonList = listOfPokemon.distinct()) }
                } else {
                    // TODO Implement a better error handling
                    _uiState.update { it.copy(isLoading = false, error = "Error loading pokemons") }
                }
            }
    }
}