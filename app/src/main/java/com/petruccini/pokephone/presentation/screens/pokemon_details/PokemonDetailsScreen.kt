package com.petruccini.pokephone.presentation.screens.pokemon_details

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.petruccini.pokephone.domain.entities.PokemonDetails
import com.petruccini.pokephone.domain.entities.Sprites
import com.petruccini.pokephone.presentation.ktx.collectAsStateLifecycleAware
import com.petruccini.pokephone.presentation.shared_components.ShowProgressBar

@Composable
fun PokemonDetailsScreen(
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    pokemonName: String
) {

    val context = LocalContext.current
    val capitalizedPokemonName = viewModel.formatPokemonName(pokemonName)
    val uiState by viewModel.uiState.collectAsStateLifecycleAware()
    if (uiState.error != null) {
        Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
    }
    if (uiState.isLoading) {
        ShowProgressBar()
    }

    LaunchedEffect(pokemonName) {
        viewModel.getPokemonDetails(pokemonName)
    }

    PokemonDetails(capitalizedPokemonName, uiState)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PokemonDetails(
    capitalizedPokemonName: String,
    uiState: PokemonDetailsUiState
) {
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
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {

                val (imageRef, columnRef) = createRefs()
                AsyncImage(model = uiState.pokemonDetails?.sprites?.frontDefault,
                    contentDescription = "Pokemon Image",
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .padding(16.dp)
                        .constrainAs(imageRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(columnRef) {
                        top.linkTo(imageRef.top, margin = 32.dp)
                        start.linkTo(imageRef.end, margin = 16.dp)
                        end.linkTo(parent.end)
                    }
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
                    Text(
                        text = "Order: ${uiState.pokemonDetails?.order}",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
        }

        if (uiState.isLoading) {
            ShowProgressBar()
        }
    }
}

@Preview
@Composable
fun PokemonDetailsPreview() {
    PokemonDetails(
        capitalizedPokemonName = "Pikachu",
        uiState = PokemonDetailsUiState(
            pokemonDetails = PokemonDetails(
                id = 25,
                name = "pikachu",
                height = 4,
                weight = 60,
                order = 35,
                sprites = Sprites(
                    frontDefault = "",
                    backDefault = "",
                ),
                types = listOf(),
            ),
            isLoading = false,
        )
    )
}