package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.ListAdapter
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.R
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data.Anime
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.retrofit.ApiService
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var animeAdapter: ListAdapter
    private lateinit var seasonSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var viewModel: ListViewModel

    private val seasonOptions = arrayOf("WINTER", "SPRING", "SUMMER", "FALL")
    private val yearOptions = Array(20) { (2024 - it).toString() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)

        seasonSpinner = view.findViewById(R.id.seasonSpinner)
        yearSpinner = view.findViewById(R.id.yearSpinner)

        val seasonAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, seasonOptions)
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        seasonSpinner.adapter = seasonAdapter

        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, yearOptions)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter

        seasonSpinner.setSelection(seasonOptions.indexOf("FALL"))
        yearSpinner.setSelection(yearOptions.indexOf("2023"))

        view.findViewById<Button>(R.id.fetchButton).setOnClickListener {
            fetchAnimeList()
        }

        viewModel.animeList.observe(viewLifecycleOwner) { animeList ->
            if (animeList.isNullOrEmpty()){
                fetchAnimeList()
            } else {
                updateUI(animeList)
            }
        }

        return view
    }

    private fun fetchAnimeList() {
        loadingProgressBar.visibility = View.VISIBLE

        val selectedSeason = seasonOptions[seasonSpinner.selectedItemPosition]
        val selectedYear = yearOptions[yearSpinner.selectedItemPosition].toInt()

        val apiService = ApiConfig.instance.create(ApiService::class.java)

        val query = """
            query GetAnimeList {
              Page(page: 1, perPage: 50) {
                media(season: $selectedSeason, seasonYear: $selectedYear, type: ANIME, sort: POPULARITY_DESC) {
                  id
                  title {
                    romaji
                  }
                  popularity
                  coverImage {
                    large
                  }
                }
              }
            }
        """.trimIndent()

        val requestBody = mapOf("query" to query)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getAnimeList(requestBody)
                val animeList = response.data.Page.media
                withContext(Dispatchers.Main) {
                    loadingProgressBar.visibility = View.GONE
                    updateUI(animeList)
                    viewModel.saveAnimeList(animeList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(animeList: List<Anime>) {
        animeAdapter = ListAdapter(animeList)
        recyclerView.adapter = animeAdapter
    }
}