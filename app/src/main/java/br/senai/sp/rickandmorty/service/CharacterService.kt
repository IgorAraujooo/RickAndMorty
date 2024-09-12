package br.senai.sp.rickandmorty.service

import br.senai.sp.rickandmorty.model.Character
import br.senai.sp.rickandmorty.model.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    @GET("character")
    fun getAllCharacters(): Call<Results>

    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Call<Character>

}