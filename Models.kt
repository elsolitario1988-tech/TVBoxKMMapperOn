package com.tvbox.kmmapper.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa un remapeo de tecla
 */
@Entity(tableName = "key_mappings")
data class KeyMapping(
    @PrimaryKey
    val originalKeyCode: Int,
    val mappedKeyCode: Int,
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Representa un perfil de aplicación
 */
@Entity(tableName = "app_profiles")
data class AppProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val appPackage: String,
    val appName: String,
    val mouseSensitivity: Float = 1.0f,
    val cursorSpeed: Float = 1.5f,
    val scrollSpeed: Float = 1.0f,
    val keyMappingsJson: String = "{}", // JSON string de remapeos
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Evento de entrada de entrada
 */
data class InputEvent(
    val type: InputEventType,
    val keyCode: Int? = null,
    val keyName: String? = null,
    val mouseX: Int = 0,
    val mouseY: Int = 0,
    val mouseButton: Int? = null,
    val mouseButtonName: String? = null,
    val scrollDirection: ScrollDirection? = null,
    val timestamp: Long = System.currentTimeMillis()
)

enum class InputEventType {
    KEY_DOWN,
    KEY_UP,
    MOUSE_MOVE,
    MOUSE_BUTTON_DOWN,
    MOUSE_BUTTON_UP,
    MOUSE_SCROLL
}

enum class ScrollDirection {
    UP,
    DOWN
}

/**
 * Estado del dispositivo USB
 */
data class UsbDeviceInfo(
    val deviceId: Int,
    val deviceName: String,
    val deviceType: UsbDeviceType,
    val vendorId: Int,
    val productId: Int,
    val isConnected: Boolean = true
)

enum class UsbDeviceType {
    KEYBOARD,
    MOUSE,
    UNKNOWN
}

/**
 * Configuración de la aplicación
 */
data class AppSettings(
    val autostartEnabled: Boolean = false,
    val mouseSensitivity: Float = 1.0f,
    val cursorSpeed: Float = 1.5f,
    val scrollSpeed: Float = 1.0f,
    val notificationsEnabled: Boolean = true,
    val darkThemeEnabled: Boolean = true,
    val lastUpdatedAt: Long = System.currentTimeMillis()
)
