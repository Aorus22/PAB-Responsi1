package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data

data class AnimeResponse(
    val data: PageData
)

data class PageData(
    val Page: Page
)

data class Page(
    val media: List<Anime>
)