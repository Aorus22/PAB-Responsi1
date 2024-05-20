package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.data.Anime
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.ui.DetailActivity

class ListAdapter(private val animeList: List<Anime>) :
    RecyclerView.Adapter<ListAdapter.AnimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]
        holder.bind(anime)
    }

    override fun getItemCount(): Int = animeList.size

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val animeTitle: TextView = itemView.findViewById(R.id.animeTitle)
        private val animePoster: ImageView = itemView.findViewById(R.id.animePoster)
        private val animePopularity: TextView = itemView.findViewById(R.id.animePopularity)

        fun bind(anime: Anime) {
            animeTitle.text = anime.title.romaji
            val popularity: Int = anime.popularity
            animePopularity.text = String.format("%,d", popularity)

            Glide.with(itemView.context).load(anime.coverImage.large).into(animePoster)

            itemView.setOnClickListener {
                val context: Context = it.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("animeId", anime.id)
                }
                context.startActivity(intent)
            }
        }
    }
}