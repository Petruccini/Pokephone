package com.petruccini.pokephone.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petruccini.pokephone.domain.entities.PokemonList
import com.petruccini.pokephone.domain.use_cases.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
): ViewModel() {

    private val _pokemonListStateFlow: MutableStateFlow<PokemonList?> = MutableStateFlow(null)
    val pokemonListStateFlow = _pokemonListStateFlow.asStateFlow()

    fun getPokemonList(page: Int) {
        viewModelScope.launch {
            getPokemonListUseCase(page).collect {
                _pokemonListStateFlow.value = it
            }
        }
    }

}