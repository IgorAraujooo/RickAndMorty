package br.senai.sp.rickandmorty.model

import android.icu.text.UnicodeSetSpanner.CountMethod

data class Info(
    val count: Int = 0,
    val pages: Int = 0,
    val next: String = "",
    val prev: String = ""
)
