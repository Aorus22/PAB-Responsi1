package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.retrofit

import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data.AnimeDetailResponse
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data.AnimeResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AnimeService {
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun getAnimeList(@Body request: Map<String, String>): AnimeResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun getAnimeDetail(@Body requestBody: Map<String, String?>): AnimeDetailResponse
}