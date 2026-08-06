package com.tvbox.kmmapper.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.tvbox.kmmapper.data.db.AppDatabase
import com.tvbox.kmmapper.data.models.InputEvent
import com.tvbox.kmmapper.data.models.InputEventType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Servicio de accesibilidad para capturar y remapear eventos de teclado
 * 
 * NOTA IMPORTANTE: Android limita lo que un servicio de accesibilidad puede hacer.
 * Este servicio se utiliza principalmente para:
 * - Monitorear eventos de accesibilidad
 * - Registrar entrada de dispositivos
 * 
 * No se puede hacer:
 * - Inyectar eventos de entrada arbitrarios (esto requeriría root)
 * - Interceptar y modificar eventos de otras aplicaciones
 * - Acceder directamente a dispositivos USB sin permisos específicos
 * 
 * Para funciones reales de remapeo, se usa InputManager API o
 * se depende de permisos de sistema en devices con acceso root.
 */
class KeyRemappingAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "KeyRemappingAccessService"
    }

    private val serviceScope = CoroutineScope(Dispatchers.Main)
    private lateinit var database: AppDatabase

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "Accessibility Service connected")
        database = AppDatabase.getInstance(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                // Cuando una vista recibe foco
                handleViewFocusEvent(event)
            }
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                // Cuando la ventana activa cambia (detectar aplicación activa)
                handleWindowStateChanged(event)
            }
            // Se pueden procesar otros tipos de eventos
        }
    }

    private fun handleViewFocusEvent(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString() ?: return
        Log.d(TAG, "View focused in package: $packageName")
    }

    private fun handleWindowStateChanged(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString() ?: return
        val className = event.className?.toString() ?: return

        Log.d(TAG, "Window changed - Package: $packageName, Class: $className")

        // Aquí se podría cambiar el perfil de remapeo basado en la app activa
        serviceScope.launch {
            val profile = database.appProfileDao().getProfileByPackage(packageName)
            if (profile != null && profile.isEnabled) {
                Log.d(TAG, "Perfil encontrado para: $packageName")
                // Aplicar configuraciones del perfil
            }
        }
    }

    override fun onInterrupt() {
        Log.d(TAG, "Accessibility Service interrupted")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Accessibility Service destroyed")
    }
}
