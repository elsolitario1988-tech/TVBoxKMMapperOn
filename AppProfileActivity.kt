package com.tvbox.kmmapper.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tvbox.kmmapper.databinding.ActivityAppProfileBinding
import com.tvbox.kmmapper.data.db.AppDatabase
import kotlinx.coroutines.launch

/**
 * Actividad para crear y gestionar perfiles de remapeo por aplicación
 */
class AppProfileActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AppProfileActivity"
    }

    private lateinit var binding: ActivityAppProfileBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)

        setupListeners()
        observeData()

        Log.d(TAG, "AppProfileActivity created")
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnAddProfile.setOnClickListener {
            addNewProfile()
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            database.appProfileDao().getAllActiveProfiles().collect { profiles ->
                displayProfiles(profiles)
            }
        }
    }

    private fun displayProfiles(profiles: List<Any>) {
        binding.llProfileContainer.removeAllViews()
        if (profiles.isEmpty()) {
            // Mostrar mensaje de "sin perfiles"
            return
        }
        // En versión completa, mostrar cada perfil en una tarjeta
        Log.d(TAG, "Mostrando ${profiles.size} perfiles")
    }

    private fun addNewProfile() {
        Log.d(TAG, "Agregando nuevo perfil")
        // En versión completa, abrir diálogo para seleccionar aplicación
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "AppProfileActivity destroyed")
    }
}
