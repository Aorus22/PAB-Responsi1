package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data.AnimeDetail
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.databinding.ActivityDetailBinding
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.retrofit.ApiService
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val apiService = ApiConfig.instance.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeId = intent.getIntExtra("animeId", 0)
        fetchAnimeDetail(animeId)
    }

    private fun fetchAnimeDetail(id: Int) {
        val query = """
            query GetAnimeDetail {
              Media(id: $id) {
                id
                title {
                  romaji
                  english
                  native
                }
                description
                coverImage {
                  large
                }
                episodes
                duration
                genres
                averageScore
                popularity
                favourites
                status
                source
                startDate {
                  year
                  month
                  day
                }
                endDate {
                  year
                  month
                  day
                }
              }
            }
        """.trimIndent()

        val requestBody = mapOf("query" to query, "variables" to null)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getAnimeDetail(requestBody)
                val animeDetail = response.data.Media
                withContext(Dispatchers.Main) {
                    updateUI(animeDetail)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun cleanHtmlTags(input: String): String {
        return android.text.Html.fromHtml(input, android.text.Html.FROM_HTML_MODE_LEGACY).toString()
    }

    private fun updateUI(animeDetail: AnimeDetail) {
        binding.apply {
            tvTitle.text = animeDetail.title.romaji
            tvDescription.text = cleanHtmlTags(animeDetail.description)
            tvGenres.text = animeDetail.genres.joinToString(", ")
            tvEpisodes.text = animeDetail.episodes.toString()
            tvDuration.text = animeDetail.duration.toString()
            tvAverageScore.text = animeDetail.averageScore.toString()
            tvPopularity.text = animeDetail.popularity.toString()
            tvFavourites.text = animeDetail.favourites.toString()
            tvStatus.text = animeDetail.status
            tvSource.text = animeDetail.source
            Glide.with(this@DetailActivity)
                .load(animeDetail.coverImage.large)
                .into(ivCoverImage)
        }
    }
}
