package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data.Anime

class ListViewModel : ViewModel() {
    private val _animeList = MutableLiveData<List<Anime>>(null)
    val animeList: LiveData<List<Anime>>
        get() = _animeList

    fun saveAnimeList(animeList: List<Anime>) {
        _animeList.value = animeList
    }
}