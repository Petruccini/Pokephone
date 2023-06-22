package com.petruccini.pokephone.domain.use_cases

import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonDetailsDataSource
import com.petruccini.pokephone.data.repositories.PokemonDetailsRepository
import com.petruccini.pokephone.domain.entities.PokemonDetails
import com.petruccini.pokephone.domain.entities.Sprites
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetPokemonDetailsUseCaseTest {

    private val remotePokemonDetailsDataSource: RemotePokemonDetailsDataSource = mock()
    private val pokemonDetailsRepositoryMock =
        PokemonDetailsRepository(remotePokemonDetailsDataSource)
    private val getPokemonDetailsUseCase = GetPokemonDetailsUseCase(pokemonDetailsRepositoryMock)

    @Test
    fun getPokemonDetailsUseCase_ShouldReturnPokemonDetails() = runTest {
        // Given
        val pokemonName = "Bulbasaur"
        val pokemonDetails = createPokemonDetailsMock()
        `when`(remotePokemonDetailsDataSource.fetchPokemonDetails(pokemonName)).thenReturn(
            flowOf(pokemonDetails)
        )

        // When
        val result = getPokemonDetailsUseCase(pokemonName)

        // Then
        result.collect {
            assertEquals(pokemonDetails, it)
        }
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