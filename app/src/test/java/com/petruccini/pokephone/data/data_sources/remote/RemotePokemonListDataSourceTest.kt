package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.PokemonServices
import com.petruccini.pokephone.data.api.model.PokemonItemResponse
import com.petruccini.pokephone.data.api.model.PokemonListResponse
import com.petruccini.pokephone.data.api.model.toPokemonList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RemotePokemonListDataSourceTest {

    private val pokemonServices: PokemonServices = mock()
    private val remotePokemonListDataSource = RemotePokemonListDataSource(pokemonServices)

    @Test
    fun fetchPokemonList_ShouldReturnListOfPokemons_WhenSuccess() = runTest {
        // Given
        val offset = 0
        val limit = 20
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
        `when`(pokemonServices.getPokemons(offset, limit)).thenReturn(Response.success(pokemonListResponse))

        // When
        val result = remotePokemonListDataSource.fetchPokemonList(offset, limit)

        // Then
        result.collect {
            assertEquals(pokemonListResponse.toPokemonList(), it)
        }
    }

    @Test
    fun fetchPokemonList_ShouldReturnNull_WhenError() = runTest {
        // Given
        val offset = 0
        val limit = 20

        `when`(pokemonServices.getPokemons(offset, limit)).thenReturn(
            Response.error(404, ResponseBody.create(null, ""))
        )

        // When
        val result = remotePokemonListDataSource.fetchPokemonList(offset, limit)

        // Then
        result.collect {
            assertNull(it)
        }
    }
}