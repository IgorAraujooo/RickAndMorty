package br.senai.sp.rickandmorty.model

data class Episode(
    val id: Int = 0,
    val name: String = "",
    val air_date: String = "",
    val episode: String = "",
    val characters: List<String> = listOf<String>(),
    val url: String = "",
    val created: String = ""
)
