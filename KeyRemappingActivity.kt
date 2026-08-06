package com.tvbox.kmmapper.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tvbox.kmmapper.databinding.ActivityKeyRemappingBinding
import com.tvbox.kmmapper.data.db.AppDatabase
import kotlinx.coroutines.launch

/**
 * Actividad para remapear teclas del teclado USB
 */
class KeyRemappingActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "KeyRemappingActivity"
    }

    private lateinit var binding: ActivityKeyRemappingBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeyRemappingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)

        setupListeners()
        observeData()

        Log.d(TAG, "KeyRemappingActivity created")
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnAddMapping.setOnClickListener {
            addNewMapping()
        }

        binding.btnResetMapping.setOnClickListener {
            resetAllMappings()
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            database.keyMappingDao().getAllActiveMappings().collect { mappings ->
                displayMappings(mappings)
            }
        }
    }

    private fun displayMappings(mappings: List<Any>) {
        binding.llMappingContainer.removeAllViews()
        if (mappings.isEmpty()) {
            // Mostrar mensaje de "sin mapeos"
            return
        }
        // En versión completa, mostrar cada mapeo en una tarjeta
        Log.d(TAG, "Mostrando ${mappings.size} mapeos")
    }

    private fun addNewMapping() {
        Log.d(TAG, "Agregando nuevo mapeo de tecla")
        // En versión completa, abrir diálogo para seleccionar tecla
    }

    private fun resetAllMappings() {
        Log.d(TAG, "Restaurando mapeos predeterminados")
        lifecycleScope.launch {
            database.keyMappingDao().deleteAllMappings()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "KeyRemappingActivity destroyed")
    }
}
