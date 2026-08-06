package com.tvbox.kmmapper.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import android.util.Log

/**
 * Receiver que detecta la conexión y desconexión de dispositivos USB
 */
class UsbDeviceReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "UsbDeviceReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        when (intent.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                Log.d(TAG, "USB Device attached")
                handleUsbDeviceAttached(context, intent)
            }
            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                Log.d(TAG, "USB Device detached")
                handleUsbDeviceDetached(context, intent)
            }
        }
    }

    private fun handleUsbDeviceAttached(context: Context, intent: Intent) {
        // En una aplicación real, aquí se obtendrían los detalles del dispositivo
        // del intent usando UsbDevice
        
        // Notificar a la aplicación sobre el nuevo dispositivo
        val broadcastIntent = Intent("com.tvbox.kmmapper.USB_DEVICE_ATTACHED")
        context.sendBroadcast(broadcastIntent)
    }

    private fun handleUsbDeviceDetached(context: Context, intent: Intent) {
        // Notificar a la aplicación sobre la desconexión
        val broadcastIntent = Intent("com.tvbox.kmmapper.USB_DEVICE_DETACHED")
        context.sendBroadcast(broadcastIntent)
    }
}
