package com.tvbox.kmmapper.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.slider.Slider
import com.tvbox.kmmapper.databinding.ActivitySettingsBinding
import com.tvbox.kmmapper.data.preferences.PreferencesProvider
import kotlinx.coroutines.launch

/**
 * Actividad de configuración de la aplicación
 */
class SettingsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SettingsActivity"
    }

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var preferencesProvider: PreferencesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesProvider = PreferencesProvider(this)

        setupListeners()
        loadSettings()

        Log.d(TAG, "SettingsActivity created")
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Autostart
        binding.switchAutostart.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                preferencesProvider.setAutostart(isChecked)
                Log.d(TAG, "Autostart: $isChecked")
            }
        }

        // Mouse Sensitivity
        binding.sliderMouseSensitivity.addOnChangeListener { _, value, _ ->
            binding.tvMouseSensitivityValue.text = String.format("%.1fx", value)
            lifecycleScope.launch {
                preferencesProvider.setMouseSensitivity(value)
            }
        }

        // Cursor Speed
        binding.sliderCursorSpeed.addOnChangeListener { _, value, _ ->
            binding.tvCursorSpeedValue.text = String.format("%.1fx", value)
            lifecycleScope.launch {
                preferencesProvider.setCursorSpeed(value)
            }
        }

        // Scroll Speed
        binding.sliderScrollSpeed.addOnChangeListener { _, value, _ ->
            binding.tvScrollSpeedValue.text = String.format("%.1fx", value)
            lifecycleScope.launch {
                preferencesProvider.setScrollSpeed(value)
            }
        }

        // Notifications
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                preferencesProvider.setNotificationsEnabled(isChecked)
                Log.d(TAG, "Notificaciones: $isChecked")
            }
        }
    }

    private fun loadSettings() {
        lifecycleScope.launch {
            preferencesProvider.getAutostart().collect { value ->
                binding.switchAutostart.isChecked = value
            }
        }

        lifecycleScope.launch {
            preferencesProvider.getMouseSensitivity().collect { value ->
                binding.sliderMouseSensitivity.value = value
                binding.tvMouseSensitivityValue.text = String.format("%.1fx", value)
            }
        }

        lifecycleScope.launch {
            preferencesProvider.getCursorSpeed().collect { value ->
                binding.sliderCursorSpeed.value = value
                binding.tvCursorSpeedValue.text = String.format("%.1fx", value)
            }
        }

        lifecycleScope.launch {
            preferencesProvider.getScrollSpeed().collect { value ->
                binding.sliderScrollSpeed.value = value
                binding.tvScrollSpeedValue.text = String.format("%.1fx", value)
            }
        }

        lifecycleScope.launch {
            preferencesProvider.getNotificationsEnabled().collect { value ->
                binding.switchNotifications.isChecked = value
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SettingsActivity destroyed")
    }
}
