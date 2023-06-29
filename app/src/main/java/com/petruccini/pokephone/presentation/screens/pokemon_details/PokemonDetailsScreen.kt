package com.petruccini.pokephone.presentation.screens.pokemon_details

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.petruccini.pokephone.presentation.ktx.collectAsStateLifecycleAware
import com.petruccini.pokephone.presentation.shared_components.ShowProgressBar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsScreen(
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    pokemonName: String
) {

    val context = LocalContext.current

    val capitalizedPokemonName = pokemonName.capitalizeFirstChar()

    val uiState by viewModel.uiState.collectAsStateLifecycleAware()

    if (uiState.error != null) {
        Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
    }

    if (uiState.isLoading) { ShowProgressBar() }

    LaunchedEffect(pokemonName) {
        viewModel.getPokemonDetails(pokemonName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$capitalizedPokemonName Details") },
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Height: ${uiState.pokemonDetails?.height}",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = "Weight: ${uiState.pokemonDetails?.weight}",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                Text(
                    text = "Order: ${uiState.pokemonDetails?.order}",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        if (uiState.isLoading) {
            ShowProgressBar()
        }
    }

}

fun String.capitalizeFirstChar() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.getDefault()
    ) else it.toString()
}