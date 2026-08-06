# ⚠️ Limitaciones de Android y Alternativas Oficiales

Este documento explica por qué algunas funciones no son posibles dentro de los límites oficiales de Android, y qué alternativas se ofrecen.

## 🔒 Limitaciones de seguridad de Android

Android está diseñado con múltiples capas de seguridad para proteger el dispositivo y los datos del usuario. TVBox KM Mapper respeta estas limitaciones.

### Lo que SÍ podemos hacer

✅ **Funciones disponibles sin permisos especiales:**
- Detectar dispositivos USB conectados
- Monitorear eventos de entrada (teclado/mouse)
- Registrar y loguear entrada de dispositivos
- Crear perfiles de configuración
- Usar APIs de accesibilidad permitidas
- Iniciar automáticamente en el boot
- Usar servicios en primer plano
- Almacenar datos locales (Room, DataStore)

✅ **Con servicio de accesibilidad habilitado:**
- Detectar aplicación activa
- Monitorear eventos de ventanas
- Leer información de UI de otras apps
- Vibraciones como retroalimentación

✅ **Con acceso root (requiere desbloqueo):**
- Inyectar eventos de entrada globales
- Capturar entrada a nivel de kernel
- Modificar configuración del sistema
- Remapeo de hardware

## ❌ Limitaciones técnicas y por qué existen

### 1. Inyección de eventos de entrada sin permisos de sistema

**Lo que NO se puede hacer:**
```
- Simular pulsaciones de tecla en otras aplicaciones
- Inyectar clics del mouse
- Enviar eventos de scroll a cualquier app
```

**Por qué:**
- Peligro de seguridad: Una app maliciosa podría simular entrada del usuario
- Fraude: Clickear automáticamente en anuncios, transferencias bancarias, etc.
- Phishing: Automatizar acciones sin consentimiento visible

**Alternativas en Android:**
```java
// ✅ Usar Instrumentation (solo en tests)
Instrumentation inst = new Instrumentation();
inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);

// ✅ Usar AccessibilityService (con limitaciones)
AccessibilityService.PerformActionRequest action = 
    new AccessibilityService.PerformActionRequest(...);

// ⚠️ Requiere ROOT:
// Inyectar a nivel de /dev/input/
```

### 2. Acceso directo a dispositivos /dev/input/

**Lo que NO se puede hacer:**
```
- Leer directamente de /dev/input/event*
- Escribir eventos a nivel de kernel
- Intercepción de entrada antes de Android
```

**Por qué:**
- Requiere permisos ROOT
- Acceso directo al driver de hardware
- Podría eludir controles de seguridad

**Alternativas en Android:**
```kotlin
// ✅ InputManager (limitado, no disponible en app user)
val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager

// ✅ AccessibilityService (opción oficial)
// Permite monitoreo, no inyección

// ⚠️ Requiere ROOT:
// exec("cat /dev/input/event0")
```

### 3. Remapeo de teclas a nivel de sistema

**Lo que NO se puede hacer:**
```
- Cambiar mappings de KeyCode globalmente
- Reasignar teclas para todas las aplicaciones
- Modificar el comportamiento del kernel de entrada
```

**Por qué:**
- Solo accesible con permisos SYSTEM
- Requiere acceso al HAL (Hardware Abstraction Layer)
- Podría interferir con funcionalidades críticas

**Alternativas en Android:**
```kotlin
// ✅ Remapeo por aplicación (usando Accessibility)
override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    // Cambiar comportamiento dentro de nuestra app
    when (event?.packageName) {
        "com.netflix.mediaclient" -> useNetflixKeyMapping()
        "com.kodi.kodi" -> useKodiKeyMapping()
    }
}

// ⚠️ Requiere ROOT:
// Editar /system/usr/keylayout/...
```

### 4. Modificar comportamiento de otras aplicaciones

**Lo que NO se puede hacer:**
```
- Inyectar entrada en la app activa
- Capturar/modificar eventos antes de que lleguen a otra app
- Interceptar eventos de teclado/mouse de forma global
```

**Por qué:**
- Protección de integridad del software
- Evitar apps maliciosas
- Garantizar que el usuario controla su dispositivo

**Alternativas en Android:**
```kotlin
// ✅ Monitorear sin modificar (Accessibility Service)
override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    when (event?.eventType) {
        AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
            Log.d("Input", "View focused: ${event.source?.text}")
            // Registrar pero no modificar
        }
    }
}

// ⚠️ Requiere ROOT:
// Usar LD_PRELOAD para interceptar syscalls
```

## 🔓 Funcionalidades con acceso ROOT

Si el TB432-B5C está desbloqueado/rooteado, estas funciones se vuelven posibles:

### Remapeo de teclas a nivel de sistema

```bash
# Modificar keylayout (requiere root)
# /system/usr/keylayout/Vendor_Manufacturer_Product.kl

KEYCODE_A    KEYCODE_B  # Mapear A → B
KEYCODE_HOME KEYCODE_BACK  # Mapear HOME → BACK
```

### Inyección de entrada con sendevent

```bash
# Enviar evento de tecla (requiere root)
sendevent /dev/input/event0 1 30 1  # KEY_DOWN para A
sendevent /dev/input/event0 1 30 0  # KEY_UP para A
```

### Interceptar entrada a nivel de kernel

```c
// Usando módulo kernel personalizado
// Interceptar y modificar eventos en /dev/input/

// ⚠️ Muy avanzado, no implementado en esta versión
```

## 📊 Comparación: Limitaciones por nivel

| Funcionalidad | Usuario Normal | Con Accessibility Service | Con ROOT |
|--------------|---|---|---|
| Detectar USB | ✅ | ✅ | ✅ |
| Monitorear entrada | ✅ | ✅ | ✅ |
| Inyectar entrada globalmente | ❌ | ❌ | ✅ |
| Remapear para el sistema | ❌ | ❌ | ✅ |
| Modificar otras apps | ❌ | ❌ | ✅ |
| Interceptar entrada de kernel | ❌ | ❌ | ✅ |
| Cambiar configuración de sistema | ❌ | ⚠️ | ✅ |

## 💡 Diseño de la aplicación

### Estrategia: Funcionar máximo sin ROOT

```kotlin
// 1. Nivel básico (sin permisos especiales)
class InputMonitorService {
    // Detectar dispositivos USB
    // Registrar eventos
    // Alertas locales
}

// 2. Con AccessibilityService
class KeyRemappingAccessibilityService {
    // Detectar app activa
    // Cambiar perfiles automáticamente
    // Monitoreo mejorado
}

// 3. Con ROOT (opcional)
// En TB432-B5C con acceso root:
// - Inyección global de eventos
// - Remapeo de hardware
// - Modificación de keylayouts
```

### Flujo de funcionalidades

```
┌─────────────────────────────────────┐
│ TVBox KM Mapper - Aplicación        │
├─────────────────────────────────────┤
│                                     │
│ ✅ Monitoreo USB (sin permisos)     │
│    └─ Detecta dispositivos          │
│    └─ Registra entrada              │
│                                     │
│ ⚠️ Remapeo (con Accessibility)      │
│    └─ Por aplicación                │
│    └─ Perfiles independientes       │
│                                     │
│ 🔓 Inyección (requiere ROOT)        │
│    └─ Remapeo global                │
│    └─ Inyección de eventos          │
│                                     │
└─────────────────────────────────────┘
```

## 🎯 Recomendaciones de uso

### Para máxima funcionalidad:

1. **Ideal**: TB432-B5C con acceso ROOT
   - Desbloquear bootloader (requiere acceso físico)
   - Instalar TWRP recovery
   - Flashear SuperSU o Magisk
   - Aplicación tiene acceso completo

2. **Bueno**: Acceso a Accesibilidad
   - Habilitar servicio en Settings
   - Perfiles por aplicación funcionan
   - Monitoreo completo

3. **Básico**: Usuario normal
   - Monitoreo y registro funcionan
   - Perfiles no aplicables
   - Datos para análisis

## 📚 Referencias de seguridad

- [Android Security & Privacy Year in Review 2023](https://security.googleblog.com/)
- [Android Security Architecture](https://source.android.com/security/architecture)
- [Android Accessibility Service API](https://developer.android.com/reference/android/accessibilityservice/AccessibilityService)

## 🤔 Preguntas frecuentes

**P: ¿Por qué no podemos inyectar eventos globalmente?**
R: Por seguridad. Una app sin control podría:
- Clickear en anuncios automáticamente
- Transferir dinero sin consentimiento
- Introducir malware

**P: ¿Necesito ROOT obligatoriamente?**
R: No. La app funciona bien sin ROOT, solo con limitaciones de remapeo global.

**P: ¿Es seguro desbloquear mi TB432-B5C?**
R: Si sabes lo que haces. Desbloquear = pérdida de seguridad. Usa si confías en la fuente.

**P: ¿Puedo usar esta app en producción?**
R: Sí, pero respeta las limitaciones de Android. No intentes eludir restricciones.

**P: ¿Qué dispositivos supporta mejor?**
R: Cualquiera con Android 5.0+, mejor si está rooteado para funciones avanzadas.

---

## 📋 Resumen

Esta aplicación:
- ✅ Respeta la seguridad de Android
- ✅ Usa APIs oficiales y documentadas
- ✅ Funciona sin permisos peligrosos
- ✅ Ofrece alternativas cuando no es posible algo
- ✅ Documenta limitaciones claramente

No intentes:
- ❌ Eludir restricciones de seguridad
- ❌ Usar exploits o vulnerabilidades
- ❌ Modificar APK para obtener permisos
- ❌ Inyectar código en otras apps

Resultado: Una aplicación segura, confiable y que respeta los principios de Android.

---

**Última actualización**: Agosto 2026  
**Versión**: 1.0.0
