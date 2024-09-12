package br.senai.sp.rickandmorty.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.senai.sp.rickandmorty.model.Character
import br.senai.sp.rickandmorty.model.Episode
import br.senai.sp.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharacterScreen(navController: NavController, characterId: String?) {
    var episodes by remember { mutableStateOf(emptyList<Episode>()) }
    var characterData by remember { mutableStateOf(Character()) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = Color(0xFF004D40)
    ) {
        Column {
            characterId?.let { id ->
                fetchCharacterDetails(id.toInt()) { character ->
                    characterData = character
                    getEpisodes(character.episode) { episodesList ->
                        episodes = episodesList
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CharacterCard(character = characterData)

            Spacer(modifier = Modifier.height(24.dp))

            EpisodeListContainer(episodes)
        }
    }
}

fun fetchCharacterDetails(id: Int, onResult: (Character) -> Unit) {
    val call = RetrofitFactory().getCharacterService().getCharacterById(id)
    call.enqueue(object : Callback<Character> {
        override fun onResponse(call: Call<Character>, response: Response<Character>) {
            response.body()?.let { onResult(it) }
        }

        override fun onFailure(call: Call<Character>, t: Throwable) {}
    })
}

@Composable
fun CharacterCard(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.size(120.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF00796B))
        ) {
            AsyncImage(model = character.image, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(16.dp))

        CharacterDetailsText(label = "Nome", value = character.name)
        CharacterDetailsText(label = "Espécie", value = character.species)
        CharacterDetailsText(label = "Origem", value = character.origin.name)
        CharacterDetailsText(label = "Localização", value = character.location.name)
        CharacterDetailsText(label = "Status", value = character.status)
        CharacterDetailsText(label = "Gênero", value = character.gender)
        CharacterDetailsText(label = "Tipo", value = character.type)
        CharacterDetailsText(label = "Aparece em", value = "${character.episode.size} episódios")
    }
}

@Composable
fun CharacterDetailsText(label: String, value: String) {
    Row(modifier = Modifier.padding(bottom = 4.dp)) {
        Text(text = "$label: ", color = Color(0xFFB2DFDB), fontWeight = FontWeight.Bold)
        Text(text = value, color = Color(0xFFE0F2F1))
    }
}

@Composable
fun EpisodeListContainer(episodes: List<Episode>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(episodes) { episode ->
            EpisodeCard(episode)
        }
    }
}

fun getEpisodes(episodeUrls: List<String>, callback: (List<Episode>) -> Unit) {
    val episodeService = RetrofitFactory().getEpisodeService()
    val episodes = mutableListOf<Episode>()

    val calls = episodeUrls.map { url ->
        val episodeId = url.substringAfterLast("/").toIntOrNull() ?: 0
        episodeService.getEpisodeById(episodeId)
    }

    calls.forEach { call ->
        call.enqueue(object : Callback<Episode> {
            override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                response.body()?.let {
                    episodes.add(it)
                    if (episodes.size == calls.size) {
                        callback(episodes)
                    }
                }
            }

            override fun onFailure(call: Call<Episode>, t: Throwable) {}
        })
    }
}

@Composable
fun EpisodeCard(episode: Episode) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Episódio: ${episode.episode}",
                color = Color(0xFF795548),
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Título: ${episode.name}",
                color = Color(0xFF795548),
                fontSize = 16.sp
            )
            Text(
                text = "Data de lançamento: ${episode.air_date}",
                color = Color(0xFF795548),
                fontSize = 16.sp
            )
        }
    }
}
