package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data

data class Anime(
    val id: Int,
    val title: AnimeTitle,
    val coverImage: AnimeCoverImage,
    val popularity: Int
)

data class AnimeTitle(
    val romaji: String
)

data class AnimeCoverImage(
    val large: String
)

data class AnimeDate(
    val year: Int,
    val month: Int,
    val day: Int
)