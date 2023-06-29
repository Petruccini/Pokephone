package com.petruccini.pokephone.presentation.screens.pokemon_list

import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.entities.PokemonList
import com.petruccini.pokephone.domain.use_cases.pokemon_list.GetPokemonListUseCase
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

    private val getPokemonListUseCase: GetPokemonListUseCase = mock()
    private val viewModel = PokemonListViewModel(getPokemonListUseCase)

    @Test
    fun loadMorePokemons_ShouldUpdate_uiState() = runTest {
        // Given
        val pokemonList = PokemonList(
            count = 0,
            pokemonItems = listOf(
                PokemonItem(id = 1, name = "Bulbasaur"),
                PokemonItem(id = 2, name = "Ivysaur")
            )
        )

        `when`(getPokemonListUseCase(0)).thenReturn(flowOf(pokemonList))

        val expected = listOf(
            PokemonItem(id = 1, name = "Bulbasaur"),
            PokemonItem(id = 2, name = "Ivysaur")
        )

        // When
        viewModel.loadMorePokemons()

        // Then
        assertEquals(expected, viewModel.uiState.value.pokemonList)
    }
}