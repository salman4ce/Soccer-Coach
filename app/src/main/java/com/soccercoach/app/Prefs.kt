package com.soccercoach.app

import android.content.Context

object Prefs {
    private fun sp(c: Context) = c.getSharedPreferences("coach", Context.MODE_PRIVATE)

    fun notificationsEnabled(c: Context) = sp(c).getBoolean("enabled", true)
    fun setNotificationsEnabled(c: Context, v: Boolean) = sp(c).edit().putBoolean("enabled", v).apply()

    fun hour(c: Context) = sp(c).getInt("hour", 8)
    fun setHour(c: Context, h: Int) = sp(c).edit().putInt("hour", h).apply()

    fun doneDrills(c: Context): Set<String> =
        HashSet(sp(c).getStringSet("done", emptySet()) ?: emptySet())

    fun toggleDrill(c: Context, id: String): Set<String> {
        val set = doneDrills(c).toMutableSet()
        if (!set.add(id)) set.remove(id)
        sp(c).edit().putStringSet("done", set).apply()
        return set
    }
}
