package com.example.domain.entity.gallery

data class GalleryModel(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)
