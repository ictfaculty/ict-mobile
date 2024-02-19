package com.example.ttu.model

import android.icu.text.CaseMap.Title
data class News(
    val id: Int,
    val title: String,
    val body: String,
    val image_url: String,
    val isActive: Boolean,
    val createdBy: String,
    val createdAt: String
)

