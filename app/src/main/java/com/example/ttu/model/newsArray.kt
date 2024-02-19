package com.example.ttu.model



data class JsonNews(
    val code: Int,
    val message: String,
    val data: MutableList<News>
)