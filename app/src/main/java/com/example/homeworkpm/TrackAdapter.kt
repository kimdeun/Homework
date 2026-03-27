package com.example.homeworkpm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(private var tracks: List<Track>) : RecyclerView.Adapter<TrackViewHolder>() {

    private var onItemClickListener: ((Track) -> Unit)? = null

    /**
     * Установить слушатель нажатия на элемент списка
     */
    fun setOnItemClickListener(listener: (Track) -> Unit) {
        onItemClickListener = listener
    }

    /**
     * Обновить список треков
     */
    fun updateTracks(newTracks: List<Track>) {
        tracks = newTracks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)

        // Обработка нажатия на элемент
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(track)
        }
    }

    override fun getItemCount(): Int = tracks.size
}