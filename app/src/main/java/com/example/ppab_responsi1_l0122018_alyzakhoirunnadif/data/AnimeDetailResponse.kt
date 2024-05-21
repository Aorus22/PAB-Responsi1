package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data
data class AnimeDetailResponse(
    val data: AnimeDetailData
)

data class AnimeDetailData(
    val Media: AnimeDetail
)

data class AnimeDetail(
    val id: Int,
    val title: AnimeTitle,
    val description: String,
    val coverImage: AnimeCoverImage,
    val episodes: Int,
    val duration: Int,
    val genres: List<String>,
    val averageScore: Int,
    val popularity: Int,
    val favourites: Int,
    val status: String,
    val source: String,
    val startdate: AnimeDate,
    val enddate: AnimeDate
)