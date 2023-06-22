package com.petruccini.pokephone.presentation.screens.pokemon_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.presentation.ktx.collectAsStateLifecycleAware

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    navigateToPokemonDetails: (String) -> Unit
) {

    val pokemonListState = viewModel.pokemonListStateFlow.collectAsStateLifecycleAware()
    val isLoading = viewModel.loadingPokemonListStateFlow.collectAsStateLifecycleAware()

    LaunchedEffect(Unit) {
        viewModel.loadMorePokemons()
    }

    Scaffold(
        topBar = {
            Text(text = "PokePhone")
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PokemonList(
                pokemonList = pokemonListState.value,
                onSelectPokemon = navigateToPokemonDetails,
                onEndOfListReached = { viewModel.loadMorePokemons() }
            )
        }

        if (isLoading.value) {
            ShowProgressBar()
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<PokemonItem>,
    onSelectPokemon: (String) -> Unit,
    onEndOfListReached: () -> Unit
) {

    val lazyListState = rememberLazyListState()
    val endOfListReached by remember { derivedStateOf { lazyListState.isScrolledToEnd() } }

    if (endOfListReached) {
        LaunchedEffect(Unit) {
            onEndOfListReached()
        }
    }

    LazyColumn(state = lazyListState) {
        items(pokemonList, key = { it.id }) {
            Row(modifier = Modifier
                .clickable { onSelectPokemon(it.name) }) {
                Text(
                    text = "${it.id} - ${it.name}", modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ShowProgressBar() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1