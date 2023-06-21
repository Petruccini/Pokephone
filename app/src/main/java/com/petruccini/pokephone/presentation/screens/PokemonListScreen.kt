package com.petruccini.pokephone.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.petruccini.pokephone.presentation.ktx.collectAsStateLifecycleAware
import kotlinx.coroutines.launch

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    navigateToPokemonDetails: (String) -> Unit
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycleScope = lifecycleOwner.lifecycleScope

    val pokemonListState = viewModel.pokemonListStateFlow.collectAsStateLifecycleAware()

    LaunchedEffect(Unit) {
        lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPokemonList(0)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        pokemonListState.value?.pokemonItems?.forEach { pokemon ->
            Row(modifier = Modifier
                .clickable { navigateToPokemonDetails(pokemon.name) }) {
                Text(text = "${pokemon.id} - ${pokemon.name}", modifier = Modifier.padding(16.dp).fillMaxWidth())
            }
        }
    }
}