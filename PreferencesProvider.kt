package com.tvbox.kmmapper.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "tvbox_kmmapper_prefs")

/**
 * Proveedor de preferencias usando DataStore
 */
class PreferencesProvider(private val context: Context) {

    companion object {
        private val KEY_AUTOSTART = booleanPreferencesKey("autostart")
        private val KEY_MOUSE_SENSITIVITY = floatPreferencesKey("mouse_sensitivity")
        private val KEY_CURSOR_SPEED = floatPreferencesKey("cursor_speed")
        private val KEY_SCROLL_SPEED = floatPreferencesKey("scroll_speed")
        private val KEY_NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        private val KEY_MONITORING_ACTIVE = booleanPreferencesKey("monitoring_active")
        private val KEY_ACCESSIBILITY_ENABLED = booleanPreferencesKey("accessibility_enabled")
        private val KEY_DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    // Autostart
    fun getAutostart(): Flow<Boolean> =
        context.dataStore.data.map { it[KEY_AUTOSTART] ?: false }

    suspend fun setAutostart(enabled: Boolean) {
        context.dataStore.edit { it[KEY_AUTOSTART] = enabled }
    }

    // Mouse Sensitivity
    fun getMouseSensitivity(): Flow<Float> =
        context.dataStore.data.map { it[KEY_MOUSE_SENSITIVITY] ?: 1.0f }

    suspend fun setMouseSensitivity(sensitivity: Float) {
        context.dataStore.edit { it[KEY_MOUSE_SENSITIVITY] = sensitivity }
    }

    // Cursor Speed
    fun getCursorSpeed(): Flow<Float> =
        context.dataStore.data.map { it[KEY_CURSOR_SPEED] ?: 1.5f }

    suspend fun setCursorSpeed(speed: Float) {
        context.dataStore.edit { it[KEY_CURSOR_SPEED] = speed }
    }

    // Scroll Speed
    fun getScrollSpeed(): Flow<Float> =
        context.dataStore.data.map { it[KEY_SCROLL_SPEED] ?: 1.0f }

    suspend fun setScrollSpeed(speed: Float) {
        context.dataStore.edit { it[KEY_SCROLL_SPEED] = speed }
    }

    // Notifications
    fun getNotificationsEnabled(): Flow<Boolean> =
        context.dataStore.data.map { it[KEY_NOTIFICATIONS_ENABLED] ?: true }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[KEY_NOTIFICATIONS_ENABLED] = enabled }
    }

    // Monitoring Active
    fun getMonitoringActive(): Flow<Boolean> =
        context.dataStore.data.map { it[KEY_MONITORING_ACTIVE] ?: false }

    suspend fun setMonitoringActive(active: Boolean) {
        context.dataStore.edit { it[KEY_MONITORING_ACTIVE] = active }
    }

    // Accessibility Service
    fun getAccessibilityEnabled(): Flow<Boolean> =
        context.dataStore.data.map { it[KEY_ACCESSIBILITY_ENABLED] ?: false }

    suspend fun setAccessibilityEnabled(enabled: Boolean) {
        context.dataStore.edit { it[KEY_ACCESSIBILITY_ENABLED] = enabled }
    }

    // Dark Theme
    fun getDarkTheme(): Flow<Boolean> =
        context.dataStore.data.map { it[KEY_DARK_THEME] ?: true }

    suspend fun setDarkTheme(enabled: Boolean) {
        context.dataStore.edit { it[KEY_DARK_THEME] = enabled }
    }
}
