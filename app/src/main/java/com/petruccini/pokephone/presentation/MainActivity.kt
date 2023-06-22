package com.petruccini.pokephone.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.petruccini.pokephone.presentation.screens.pokemon_details.PokemonDetailsScreen
import com.petruccini.pokephone.presentation.screens.pokemon_list.PokemonListScreen
import com.petruccini.pokephone.presentation.theme.PokephoneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokephoneTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pokemon_list") {
        composable("pokemon_list") {
            PokemonListScreen { pokemonName ->
                Log.d("MainActivity", "pokemonName: $pokemonName")
                navController.navigate("pokemon_details/$pokemonName")
            }
        }
        composable("pokemon_details/{pokemonName}") { backStackEntry ->
            backStackEntry.arguments?.getString("pokemonName")?.let { pokemonName ->
                Log.d("MainActivity", "pokemonName: $pokemonName")
                PokemonDetailsScreen(pokemonName = pokemonName)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokephoneTheme {
        MainScreen()
    }
}