package com.petruccini.pokephone.domain.use_cases

import com.petruccini.pokephone.data.data_sources.remote.RemotePokemonListDataSource
import com.petruccini.pokephone.data.repositories.PokemonListRepositoryImpl
import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.domain.entities.PokemonList
import com.petruccini.pokephone.domain.use_cases.pokemon_list.GetPokemonListUseCase
import com.petruccini.pokephone.domain.use_cases.pokemon_list.POKEMON_LIST_LIMIT
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
class GetPokemonListUseCaseTest {

    private val remotePokemonListDataSource: RemotePokemonListDataSource = mock()
    private val pokemonListRepositoryImplMock = PokemonListRepositoryImpl(remotePokemonListDataSource)
    private val getPokemonListUseCase = GetPokemonListUseCase(pokemonListRepositoryImplMock)

    @Test
    fun getPokemonListUseCase_ShouldReturnListOfPokemons() = runTest {
        // Given
        val pokemonList = PokemonList(1, listOf(PokemonItem(0, "Bulbasaur")))
        `when`(remotePokemonListDataSource.fetchPokemonList(0, POKEMON_LIST_LIMIT)).thenReturn(flowOf(pokemonList))

        // When
        val result = getPokemonListUseCase(0)

        // Then
        result.collect {
            assertEquals(pokemonList, it)
        }
    }
}