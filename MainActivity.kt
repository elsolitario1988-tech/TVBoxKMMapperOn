package com.tvbox.kmmapper.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tvbox.kmmapper.databinding.ActivityMainBinding
import com.tvbox.kmmapper.data.db.AppDatabase
import com.tvbox.kmmapper.data.models.InputEvent
import com.tvbox.kmmapper.data.preferences.PreferencesProvider
import com.tvbox.kmmapper.service.InputMonitorService
import kotlinx.coroutines.launch

/**
 * Actividad principal de la aplicación
 * Muestra el estado de los dispositivos y el monitor de entrada en tiempo real
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencesProvider: PreferencesProvider
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesProvider = PreferencesProvider(this)
        database = AppDatabase.getInstance(this)

        initializeUI()
        setupListeners()
        observeData()

        Log.d(TAG, "MainActivity created")
    }

    private fun initializeUI() {
        // Permitir scroll en el log de eventos
        binding.tvEventLog.movementMethod = ScrollingMovementMethod()
        binding.tvEventLog.text = "Iniciando monitoreo...\n"

        // Configurar iconos y estados iniciales
        updateDeviceStatus()
    }

    private fun setupListeners() {
        binding.btnStartMonitoring.setOnClickListener {
            startMonitoring()
        }

        binding.btnStopMonitoring.setOnClickListener {
            stopMonitoring()
        }

        binding.btnClearLog.setOnClickListener {
            clearLog()
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Agregar más botones en versiones futuras
    }

    private fun observeData() {
        lifecycleScope.launch {
            preferencesProvider.getMonitoringActive().collect { isActive ->
                updateMonitoringStatus(isActive)
            }
        }

        lifecycleScope.launch {
            preferencesProvider.getMouseSensitivity().collect { sensitivity ->
                Log.d(TAG, "Sensibilidad del mouse actualizada: $sensitivity")
            }
        }

        // Observar cambios en dispositivos USB (en versión completa)
    }

    private fun startMonitoring() {
        Log.d(TAG, "Iniciando monitoreo")

        val intent = Intent(this, InputMonitorService::class.java).apply {
            action = InputMonitorService.ACTION_START
        }
        startForegroundService(intent)

        lifecycleScope.launch {
            preferencesProvider.setMonitoringActive(true)
        }

        appendLog("✓ Monitoreo iniciado")
    }

    private fun stopMonitoring() {
        Log.d(TAG, "Deteniendo monitoreo")

        val intent = Intent(this, InputMonitorService::class.java).apply {
            action = InputMonitorService.ACTION_STOP
        }
        startService(intent)

        lifecycleScope.launch {
            preferencesProvider.setMonitoringActive(false)
        }

        appendLog("✗ Monitoreo detenido")
    }

    private fun updateMonitoringStatus(isActive: Boolean) {
        binding.btnStartMonitoring.isEnabled = !isActive
        binding.btnStopMonitoring.isEnabled = isActive
    }

    private fun updateDeviceStatus() {
        // En una versión completa, esto verificaría dispositivos USB reales
        binding.tvKeyboardStatus.text = "Desconectado"
        binding.tvMouseStatus.text = "Desconectado"
    }

    private fun appendLog(message: String) {
        val timestamp = java.text.SimpleDateFormat("HH:mm:ss").format(java.util.Date())
        val currentText = binding.tvEventLog.text.toString()
        val newText = "[$timestamp] $message\n$currentText"
        binding.tvEventLog.text = newText.take(5000) // Limitar tamaño del log
    }

    private fun clearLog() {
        binding.tvEventLog.text = "Registro limpiado.\n"
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity paused")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity destroyed")
    }
}
