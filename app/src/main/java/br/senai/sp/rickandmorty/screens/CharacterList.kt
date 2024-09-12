package br.senai.sp.rickandmorty.screens

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.rickandmorty.model.Character
import br.senai.sp.rickandmorty.model.Results
import br.senai.sp.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharacterList(controleDeNavegacao: NavController) {

    var charactersList by remember {
        mutableStateOf(listOf<Character>())
    }

    val charactersCall = RetrofitFactory()
        .getCharacterService()
        .getAllCharacters()

    charactersCall.enqueue(
        object : Callback<Results>{
            override fun onResponse(
                call: Call<Results>,
                response: Response<Results>) {
                charactersList = response.body()!!.results
            }

            override fun onFailure(
                call: Call<Results>,
                response: Throwable) {

            }

        }
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF004D40)
    ) {
        Column (
            modifier = Modifier
                .padding(16.dp)
        ){
            Text(
                text = "Rick and Morty",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF80DEEA),
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(charactersList){ character ->
                    CharacterCard(character, controleDeNavegacao)
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character, controleDeNavegacao: NavController) {
    val context = LocalContext.current
    Card (
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(110.dp)
            .clickable{
                controleDeNavegacao.navigate("characterDetails/${character.id}")
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xff80DEEA))
    ) {
        Row {
            Card (
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 10.dp, top = 10.dp)
            ){
                AsyncImage(
                    model = character.image,
                    contentDescription = ""
                )
            }
            Column {
                Text(
                    text = "Nome do personagem:",
                    color = Color(0xFFD32F2F),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 10.dp)
                )
                Text(
                    text = "${character.name}",
                    color = Color(0xFFD32F2F),
                    modifier = Modifier.padding(start = 4.dp),
                )
                Text(
                    text = "Espécie do personagem:",
                    color = Color(0xFFD32F2F),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                )
                Text(
                    text = "${character.species}",
                    color = Color(0xFFD32F2F),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
