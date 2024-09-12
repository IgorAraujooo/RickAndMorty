package br.senai.sp.rickandmorty.service

import retrofit2.Call
import br.senai.sp.rickandmorty.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeService {

    @GET("episode")
    fun getAllEpisodes(): Call<List<Episode>>

    @GET("episode/{id}")
    fun getEpisodeById(@Path("id") id: Int): Call<Episode>
}