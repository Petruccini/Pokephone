package com.petruccini.pokephone.data.data_sources.remote

import com.petruccini.pokephone.data.api.services.pokemon_list.PokemonListService
import com.petruccini.pokephone.data.api.services.pokemon_list.model.PokemonApiModel
import com.petruccini.pokephone.data.api.services.pokemon_list.model.PokemonListApiModel
import com.petruccini.pokephone.data.api.services.pokemon_list.model.toPokemonList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RemotePokemonListDataSourceTest {

    private val pokemonListService: PokemonListService = mock()
    private val remotePokemonListDataSource = RemotePokemonListDataSource(pokemonListService)

    @Test
    fun fetchPokemonList_ShouldReturnListOfPokemons_WhenSuccess() = runTest {
        // Given
        val offset = 0
        val limit = 20
        val pokemonListApiModel = PokemonListApiModel(
            count = 3,
            next = "",
            previous = "",
            listOf(
                PokemonApiModel(name = "Bulbasaur", url = "/1/"),
                PokemonApiModel(name = "Ivysaur", url = "/2/"),
                PokemonApiModel(name = "Venusaur", url = "/3/"),
            )
        )
        `when`(pokemonListService.getPokemons(offset, limit)).thenReturn(Response.success(pokemonListApiModel))

        // When
        val result = remotePokemonListDataSource.fetchPokemonList(offset, limit)

        // Then
        assertEquals(pokemonListApiModel.toPokemonList(), result)
    }

    @Test
    fun fetchPokemonList_ShouldReturnNull_WhenError() = runTest {
        // Given
        val offset = 0
        val limit = 20

        `when`(pokemonListService.getPokemons(offset, limit)).thenReturn(
            Response.error(404, ResponseBody.create(null, ""))
        )

        // When
        val result = remotePokemonListDataSource.fetchPokemonList(offset, limit)

        // Then
        assertNull(result)
    }
}