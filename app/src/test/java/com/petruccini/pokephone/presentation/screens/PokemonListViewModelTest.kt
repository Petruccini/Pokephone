package com.petruccini.pokephone.presentation.screens

import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.entities.PokemonList
import com.petruccini.pokephone.domain.use_cases.GetPokemonPageUseCase
import com.petruccini.pokephone.presentation.screens.pokemon_list.PokemonListViewModel
import com.petruccini.pokephone.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PokemonListViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPokemonPageUseCase: GetPokemonPageUseCase = mock()
    private val viewModel = PokemonListViewModel(getPokemonPageUseCase)

    @Test
    fun getPokemonList_ShouldUpdate_pokemonListStateFlow() = runTest {
        // Given
        val pokemonList = PokemonList(
            count = 0,
            pokemonItems = listOf(
                PokemonItem(id = 1, name = "Bulbasaur"),
                PokemonItem(id = 2, name = "Ivysaur")
            )
        )

        `when`(getPokemonPageUseCase(0)).thenReturn(flowOf(pokemonList))

        val expected = listOf(
            PokemonItem(id = 1, name = "Bulbasaur"),
            PokemonItem(id = 2, name = "Ivysaur")
        )

        // When
        viewModel.loadMorePokemons()

        // Then
        assertEquals(expected, viewModel.pokemonListStateFlow.value)
    }
}