package com.example.homeworkpm

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistory(private val sharedPreferences: SharedPreferences) {

    private val gson = Gson()
    private val key = "search_history"
    private val maxSize = 10

    /**
     * Получить список треков из истории
     */
    fun getHistory(): List<Track> {
        val json = sharedPreferences.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<MutableList<Track>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    /**
     * Добавить трек в историю
     * - Если трек уже есть — удаляем старую запись
     * - Добавляем в начало списка
     * - Обрезаем до 10 треков
     */
    fun addTrack(track: Track) {
        val history = getHistory().toMutableList()

        // Удаляем, если трек с таким trackId уже есть в истории
        history.removeAll { it.trackId == track.trackId }

        // Добавляем новый трек в начало списка
        history.add(0, track)

        // Оставляем только последние 10 треков
        while (history.size > maxSize) {
            history.removeAt(history.size - 1)
        }

        // Сохраняем обновлённую историю
        saveHistory(history)
    }

    /**
     * Очистить всю историю
     */
    fun clearHistory() {
        saveHistory(emptyList())
    }

    /**
     * Проверить, пустая ли история
     */
    fun isEmpty(): Boolean = getHistory().isEmpty()

    /**
     * Сохранить список треков в SharedPreferences
     */
    private fun saveHistory(history: List<Track>) {
        val json = gson.toJson(history)
        sharedPreferences.edit().putString(key, json).apply()
    }
}