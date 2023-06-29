package com.petruccini.pokephone.presentation.screens.pokemon_details

import com.petruccini.pokephone.domain.entities.PokemonDetails
import com.petruccini.pokephone.domain.entities.Sprites
import com.petruccini.pokephone.domain.use_cases.pokemon_details.GetPokemonDetailsUseCase
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
class PokemonDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase = mock()
    private val viewModel = PokemonDetailsViewModel(getPokemonDetailsUseCase)

    @Test
    fun getPokemonDetails_ShouldUpdate_uiState() = runTest {
        // Given
        val pokemonName = "Bulbasaur"
        val pokemonDetails = createPokemonDetailsMock()
        `when`(getPokemonDetailsUseCase(pokemonName)).thenReturn(flowOf(pokemonDetails))

        // When
        viewModel.getPokemonDetails(pokemonName)

        // Then
        assertEquals(pokemonDetails, viewModel.uiState.value.pokemonDetails)
    }

    private fun createPokemonDetailsMock() = PokemonDetails(
        name = "Bulbasaur",
        id = 1,
        height = 7,
        weight = 69,
        order = 1,
        types = listOf("grass", "poison"),
        sprites = Sprites(
            frontDefault = "pokemon/1.png",
            backDefault = "pokemon/back/1.png",
        )
    )
}