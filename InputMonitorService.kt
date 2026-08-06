package com.tvbox.kmmapper.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.tvbox.kmmapper.R
import com.tvbox.kmmapper.data.db.AppDatabase
import com.tvbox.kmmapper.data.models.InputEvent
import com.tvbox.kmmapper.data.models.InputEventType
import com.tvbox.kmmapper.data.preferences.PreferencesProvider
import com.tvbox.kmmapper.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Servicio en primer plano para monitorear eventos de entrada
 * de teclado y mouse USB
 */
class InputMonitorService : Service() {

    companion object {
        private const val TAG = "InputMonitorService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "tvbox_kmmapper_input"
        const val ACTION_START = "com.tvbox.kmmapper.ACTION_START"
        const val ACTION_STOP = "com.tvbox.kmmapper.ACTION_STOP"
        const val EXTRA_INPUT_EVENT = "extra_input_event"

        private var inputEvents: MutableList<InputEvent> = mutableListOf()

        fun getInputEvents(): List<InputEvent> = inputEvents.takeLast(100)

        fun addInputEvent(event: InputEvent) {
            inputEvents.add(event)
            if (inputEvents.size > 1000) {
                inputEvents = inputEvents.takeLast(500).toMutableList()
            }
        }
    }

    private val serviceScope = CoroutineScope(Dispatchers.Main)
    private lateinit var preferencesProvider: PreferencesProvider
    private lateinit var database: AppDatabase
    private var isMonitoring = false

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "InputMonitorService created")
        preferencesProvider = PreferencesProvider(this)
        database = AppDatabase.getInstance(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return when (intent?.action) {
            ACTION_START -> {
                startMonitoring()
                START_STICKY
            }
            ACTION_STOP -> {
                stopMonitoring()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                START_NOT_STICKY
            }
            else -> START_STICKY
        }
    }

    private fun startMonitoring() {
        if (isMonitoring) return

        isMonitoring = true
        Log.d(TAG, "Monitoreo iniciado")

        // Iniciar notificación de primer plano
        startForeground(NOTIFICATION_ID, createNotification())

        serviceScope.launch {
            preferencesProvider.setMonitoringActive(true)
        }

        // En una aplicación real, aquí se conectaría a los dispositivos USB
        // usando InputManager o raw input events
    }

    private fun stopMonitoring() {
        if (!isMonitoring) return

        isMonitoring = false
        Log.d(TAG, "Monitoreo detenido")

        serviceScope.launch {
            preferencesProvider.setMonitoringActive(false)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_message))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_service),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Canal para notificaciones del servicio de monitoreo"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        stopMonitoring()
        Log.d(TAG, "InputMonitorService destroyed")
    }
}
