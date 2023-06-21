package com.petruccini.pokephone.presentation.screens

import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.entities.PokemonList
import com.petruccini.pokephone.domain.use_cases.GetPokemonListUseCase
import com.petruccini.pokephone.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPokemonListUseCase: GetPokemonListUseCase = mock()
    private val viewModel = PokemonListViewModel(getPokemonListUseCase)

    @Test
    fun getPokemonList_ShouldUpdate_pokemonListStateFlow() = runTest {
        // Given
        val page = 0
        val pokemonList = PokemonList(
            count = 0,
            pokemonItems = listOf(
                PokemonItem(id = 1, name = "Bulbasaur"),
                PokemonItem(id = 2, name = "Ivysaur"),
                PokemonItem(id = 3, name = "Venusaur")
            )
        )
        `when`(getPokemonListUseCase(page)).thenReturn(flowOf(pokemonList))

        // When
        viewModel.getPokemonList(page)

        // Then
        assertEquals(pokemonList, viewModel.pokemonListStateFlow.value)
    }
}