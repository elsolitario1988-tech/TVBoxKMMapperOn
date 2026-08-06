package com.tvbox.kmmapper.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.tvbox.kmmapper.data.preferences.PreferencesProvider
import com.tvbox.kmmapper.service.InputMonitorService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Receiver que inicia el monitoreo automáticamente cuando se enciende el TV Box
 */
class BootReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_LOCKED_BOOT_COMPLETED -> {
                Log.d(TAG, "Boot completed, checking autostart setting")
                handleBootCompleted(context)
            }
        }
    }

    private fun handleBootCompleted(context: Context) {
        val preferencesProvider = PreferencesProvider(context)

        CoroutineScope(Dispatchers.Main).launch {
            // Recopilar el valor en una corrutina
            preferencesProvider.getAutostart().collect { isAutostart ->
                if (isAutostart) {
                    Log.d(TAG, "Autostart enabled, starting InputMonitorService")
                    val intent = Intent(context, InputMonitorService::class.java).apply {
                        action = InputMonitorService.ACTION_START
                    }
                    context.startForegroundService(intent)
                } else {
                    Log.d(TAG, "Autostart disabled")
                }
            }
        }
    }
}

// Nota: collect() se usa aquí, pero en un contexto real se podría usar
// una solución más eficiente. Por simplicidad, usamos collect.
