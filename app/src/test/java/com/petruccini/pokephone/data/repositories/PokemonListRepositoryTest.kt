package com.petruccini.pokephone.data.repositories

import com.petruccini.pokephone.data.api.services.pokemon_list.PokemonListService
import com.petruccini.pokephone.data.api.services.pokemon_list.model.PokemonItemResponse
import com.petruccini.pokephone.data.api.services.pokemon_list.model.PokemonListResponse
import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonListDataSource
import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.entities.PokemonList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PokemonListRepositoryTest {

    private val pokemonListService: PokemonListService = mock()
    private val remotePokemonListDataSource = RemotePokemonListDataSource(pokemonListService)
    private val pokemonListRepository = PokemonListRepository(remotePokemonListDataSource)

    @Test
    fun fetchPokemonList_ShouldReturnListOfPokemons() = runTest {
        // Given
        val offset = 0
        val limit = 3
        val pokemonListResponse = PokemonListResponse(
            count = 3,
            next = "",
            previous = "",
            listOf(
                PokemonItemResponse(name = "Bulbasaur", url = "/1/"),
                PokemonItemResponse(name = "Ivysaur", url = "/2/"),
                PokemonItemResponse(name = "Venusaur", url = "/3/"),
            )
        )
        val expected = PokemonList(
            count = 3,
            pokemonItems = listOf(
                PokemonItem(id = 1, name = "Bulbasaur"),
                PokemonItem(id = 2, name = "Ivysaur"),
                PokemonItem(id = 3, name = "Venusaur"),
            )
        )


        `when`(pokemonListService.getPokemons(offset, limit)).thenReturn(Response.success(pokemonListResponse))

        // When
        val result = pokemonListRepository.fetchPokemonList(offset, limit)

        // Then
        result.collect {
            assertEquals(expected, it)
        }
    }
}