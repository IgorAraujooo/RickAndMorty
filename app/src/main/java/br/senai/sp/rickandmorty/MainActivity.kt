package br.senai.sp.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.rickandmorty.ui.theme.RickAndMortyTheme
import br.senai.sp.rickandmorty.screens.CharacterList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                val controleDeNavegacao = rememberNavController()

                NavHost(
                    navController = controleDeNavegacao,
                    startDestination = "characterList"
                )
                {
                    composable("characterList") {
                        CharacterList(controleDeNavegacao)
                    }
                    composable(
                        route = "characterDetails/{id}"
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")
                        CharacterDetail(controleDeNavegacao, id)
                    }

                }
            }
        }
    }
}
