package com.example.homeworkpm

import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String
) {
    companion object {
        fun fromDto(dto: TrackDto): Track {
            return Track(
                trackId = dto.trackId ?: 0,
                trackName = dto.trackName ?: "",
                artistName = dto.artistName ?: "",
                trackTime = dto.trackTimeMillis?.let {
                    SimpleDateFormat("mm:ss", Locale.getDefault()).format(it)
                } ?: "00:00",
                artworkUrl100 = dto.artworkUrl100 ?: ""
            )
        }
    }
}