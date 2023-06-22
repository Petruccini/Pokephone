package com.petruccini.pokephone.presentation.screens.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.use_cases.GetPokemonPageUseCase
import com.petruccini.pokephone.domain.use_cases.POKEMON_LIST_LIMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonPageUseCase: GetPokemonPageUseCase
) : ViewModel() {

    private val _pokemonListStateFlow: MutableStateFlow<List<PokemonItem>> =
        MutableStateFlow(listOf())
    val pokemonListStateFlow = _pokemonListStateFlow.asStateFlow()

    private val _loadingPokemonListStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingPokemonListStateFlow = _loadingPokemonListStateFlow.asStateFlow()

    private var loadedAllPokemons: Boolean  = false

    fun loadMorePokemons() {
        if (loadingPokemonListStateFlow.value) return
        if (loadedAllPokemons) return

        viewModelScope.launch {
            val currentPage = pokemonListStateFlow.value.size / POKEMON_LIST_LIMIT
            getNewPokemonsPage(currentPage)
        }
    }

    private suspend fun getNewPokemonsPage(
        currentPage: Int
    ) {
        _loadingPokemonListStateFlow.value = true
        getPokemonPageUseCase(currentPage).collect {
            if (it != null) {
                val list = _pokemonListStateFlow.value.toMutableList()
                list.addAll(it.pokemonItems)
                if (list.size == it.count) loadedAllPokemons = true
                _pokemonListStateFlow.value = list
            }
            _loadingPokemonListStateFlow.value = false
        }
    }

}