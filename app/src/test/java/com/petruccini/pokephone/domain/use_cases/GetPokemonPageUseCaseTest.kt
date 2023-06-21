package com.petruccini.pokephone.domain.use_cases

import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonListDataSource
import com.petruccini.pokephone.data.repositories.PokemonListRepository
import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.entities.PokemonList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class GetPokemonPageUseCaseTest {

    private val remotePokemonListDataSource: RemotePokemonListDataSource = mock()
    private val pokemonListRepositoryMock = PokemonListRepository(remotePokemonListDataSource)
    private val getPokemonPageUseCase = GetPokemonPageUseCase(pokemonListRepositoryMock)

    @Test
    fun getPokemonListUseCase_ShouldReturnListOfPokemons() = runTest {
        // Given
        val pokemonList = PokemonList(1, listOf(PokemonItem(0, "Bulbasaur")))
        `when`(remotePokemonListDataSource.fetchPokemonList(0, POKEMON_LIST_LIMIT)).thenReturn(flowOf(pokemonList))

        // When
        val result = getPokemonPageUseCase(0)

        // Then
        result.collect {
            assertEquals(pokemonList, it)
        }
    }
}