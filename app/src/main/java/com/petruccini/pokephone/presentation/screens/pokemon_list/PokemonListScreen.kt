package com.petruccini.pokephone.presentation.screens.pokemon_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.petruccini.pokephone.domain.entities.PokemonItem
import com.petruccini.pokephone.presentation.ktx.collectAsStateLifecycleAware
import com.petruccini.pokephone.presentation.shared_components.ShowProgressBar

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    navigateToPokemonDetails: (String) -> Unit
) {

    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateLifecycleAware()

    if (uiState.error != null) {
        Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
    }

    PokemonList(
        uiState,
        navigateToPokemonDetails,
        onEndOfListReached = {
            viewModel.loadMorePokemons()
        }, formatPokemonName = {
            viewModel.formatPokemonName(it)
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PokemonList(
    uiState: PokemonListUiState,
    navigateToPokemonDetails: (String) -> Unit,
    onEndOfListReached: () -> Unit,
    formatPokemonName: (String) -> String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "PokePhone") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PokemonScrollableList(
                pokemonList = uiState.pokemonList,
                onSelectPokemon = navigateToPokemonDetails,
                onEndOfListReached = onEndOfListReached,
                formatPokemonName = formatPokemonName
            )
        }

        if (uiState.isLoading) {
            ShowProgressBar()
        }
    }
}

@Composable
fun PokemonScrollableList(
    pokemonList: List<PokemonItem>,
    onSelectPokemon: (String) -> Unit,
    onEndOfListReached: () -> Unit,
    formatPokemonName: (String) -> String
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
            PokemonListItem(onSelectPokemon, it, formatPokemonName)
        }
    }
}

@Composable
private fun PokemonListItem(
    onSelectPokemon: (String) -> Unit,
    pokemonItem: PokemonItem,
    formatPokemonName: (String) -> String
) {
    Row(modifier = Modifier
        .clickable { onSelectPokemon(pokemonItem.name) }
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 10.dp)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = pokemonItem.id.toFourDigitString(),
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                fontWeight = MaterialTheme.typography.labelSmall.fontWeight,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
            )
        }
        Text(
            text = formatPokemonName(pokemonItem.name),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            modifier = Modifier
                .padding(14.dp)
        )
    }
}

@Preview
@Composable
fun PokemonListPreview() {
    PokemonList(
        PokemonListUiState(
            listOf(
                PokemonItem(
                    id = 1,
                    name = "Bulbasaur"
                ),
                PokemonItem(
                    id = 2,
                    name = "Ivysaur"
                ),
            ),
            isLoading = false,
            error = null
        ),
        navigateToPokemonDetails = {},
        onEndOfListReached = {},
        formatPokemonName = { it }
    )
}

fun Int.toFourDigitString(): String {
    return String.format("%04d", this)
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1