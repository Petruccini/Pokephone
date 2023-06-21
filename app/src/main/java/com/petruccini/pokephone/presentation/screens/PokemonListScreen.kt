package com.petruccini.pokephone.presentation.screens

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
import com.petruccini.pokephone.presentation.ktx.collectAsStateLifecycleAware

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    navigateToPokemonDetails: (String) -> Unit
) {

    val pokemonListState = viewModel.pokemonListStateFlow.collectAsStateLifecycleAware()
    val isLoading = viewModel.loadingPokemonListStateFlow.collectAsStateLifecycleAware()

    val listState = rememberLazyListState()
    val endOfListReached by remember { derivedStateOf { listState.isScrolledToEnd() }}

    if (isLoading.value) { ShowProgressBar() }

    LaunchedEffect(endOfListReached) {
        viewModel.loadMorePokemons()
    }

    LazyColumn(state = listState) {
        items(pokemonListState.value) {
            Row(modifier = Modifier
                .clickable { navigateToPokemonDetails(it.name) }) {
                Text(text = "${it.id} - ${it.name}", modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth())
            }
        }
    }
}


@Composable
fun ShowProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1