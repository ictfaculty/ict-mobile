package com.example.ttu.model

import com.example.ttu.model.News


data class JsonNews(
    val Code: Int,
    val Message: String,
    val Data: List<News>

)