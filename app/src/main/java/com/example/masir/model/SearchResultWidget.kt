package com.example.masir.model

import com.google.gson.annotations.SerializedName

data class SearchResultWidget(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>
)