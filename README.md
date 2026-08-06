# TVBox KM Mapper

Aplicación Android para detectar, monitorear y mapear entrada de teclado USB y mouse USB en TV Box.

## 📋 Características

- ✅ Detección automática de teclado y mouse USB
- ✅ Monitor de entrada en tiempo real
- ✅ Remapeo de teclas configurables
- ✅ Perfiles por aplicación
- ✅ Ajuste de sensibilidad del mouse
- ✅ Control de velocidad de cursor y scroll
- ✅ Inicio automático al encender el TV Box
- ✅ Interfaz optimizada para Android TV
- ✅ Tema oscuro Material Design 3
- ✅ Servicio en primer plano

## 🛠️ Requisitos Técnicos

- **Android Studio**: Última versión (Flamingo o superior)
- **Gradle**: 8.1.0
- **Kotlin**: 1.9.10
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34

## 📦 Dependencias Principales

- AndroidX Core & Lifecycle
- Material Design 3
- Android TV Foundation & Material
- Room Database
- DataStore Preferences
- Kotlin Coroutines
- Jetpack Compose (deshabilitado por defecto)

## 🚀 Compilación

### Desde Android Studio

1. Abrir Android Studio
2. Seleccionar `File > Open` y navegar a la carpeta del proyecto
3. Esperar a que se descarguen las dependencias
4. Seleccionar `Build > Build Bundle(s) / APK(s) > Build APK(s)`
5. El APK se generará en `app/build/outputs/apk/debug/`

### Desde línea de comandos

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requiere keystore configurado)
./gradlew assembleRelease

# Build Bundle para Play Store
./gradlew bundleRelease
```

### Compilación en dispositivo móvil (CodeAssist/AIDE)

```bash
# Usando CodeAssist o similar
1. Abrir el proyecto ZIP
2. Sincronizar Gradle
3. Compilar como normal
```

## 📱 Instalación

### En TV Box

```bash
# Usar adb para instalar
adb install -r app/build/outputs/apk/debug/app-debug.apk

# O copiar directamente el APK y usar un instalador
```

## ⚙️ Configuración

### Permisos Requeridos

La aplicación requiere los siguientes permisos:

- `RECEIVE_BOOT_COMPLETED` - Para iniciar automáticamente
- `FOREGROUND_SERVICE` - Para servicio en primer plano
- `BIND_ACCESSIBILITY_SERVICE` - Para monitoreo de entrada (opcional)
- Permisos de almacenamiento (Android 12 o inferior)

### Pasos iniciales

1. **Habilitar Servicio de Accesibilidad** (opcional):
   - Ajustes > Accesibilidad > Servicios
   - Buscar "TVBox KM Mapper"
   - Habilitar el servicio

2. **Configurar Autostart**:
   - Abrir la app
   - Ir a Configuración
   - Habilitar "Iniciar automáticamente"

3. **Conectar dispositivos USB**:
   - Conectar teclado y mouse USB al TV Box
   - Esperar a que la app detecte los dispositivos

## 🎮 Uso

### Monitor Principal

1. **Iniciar Monitoreo**: Presiona el botón "Iniciar Monitoreo"
2. **Presionar teclas**: El monitor mostrará la última tecla presionada
3. **Mover mouse**: Se mostrarán las coordenadas del cursor
4. **Ver log**: El registro de eventos se actualiza en tiempo real

### Remapeo de Teclas

1. Ir a "Remapeo de Teclas"
2. Presionar "Agregar Mapeo"
3. Presionar la tecla que deseas remapear
4. Seleccionar la tecla de destino
5. Guardar la configuración

### Perfiles por Aplicación

1. Ir a "Perfiles"
2. Presionar "Agregar Perfil"
3. Seleccionar la aplicación
4. Configurar remapeos específicos para esa aplicación
5. El perfil se aplicará automáticamente cuando la app esté activa

## 🔒 Notas de Seguridad y Limitaciones

### Lo que SÍ puede hacer:

- ✅ Detectar y monitorear entrada de dispositivos USB
- ✅ Registrar eventos de entrada
- ✅ Cambiar configuraciones locales
- ✅ Crear perfiles por aplicación
- ✅ Usar APIs de accesibilidad permitidas

### Lo que NO puede hacer (por limitaciones de Android):

- ❌ Inyectar eventos de entrada sin permisos de sistema
- ❌ Modificar entrada de otras aplicaciones
- ❌ Acceder directo a drivers USB sin APIs oficiales
- ❌ Eludir restricciones de seguridad de otras apps

### Nota sobre root/sistema:

Algunas funciones avanzadas de remapeo requieren:
- Acceso root al dispositivo
- O permisos SYSTEM en el firmware
- O APIs específicas del fabricante

En TB432-B5C con acceso root, se pueden implementar:
- Remapeo a nivel de kernel
- Inyección de eventos globales
- Emulación de dispositivo

## 📁 Estructura del Proyecto

```
TVBoxKMMapper/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/tvbox/kmmapper/
│   │       │   ├── data/
│   │       │   │   ├── db/        (Room Database)
│   │       │   │   ├── dao/       (Data Access Objects)
│   │       │   │   ├── models/    (Data classes)
│   │       │   │   ├── preferences/ (DataStore)
│   │       │   │   └── repository/ (Repositorios)
│   │       │   ├── service/       (Servicios)
│   │       │   ├── receiver/      (Broadcast Receivers)
│   │       │   └── ui/            (Actividades)
│   │       └── res/
│   │           ├── layout/        (Layouts XML)
│   │           ├── values/        (Strings, colores, temas)
│   │           ├── drawable/      (Imágenes vectoriales)
│   │           └── xml/           (Configuración)
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## 🔄 Ciclo de desarrollo

### Para actualizar la app:

1. Modificar código Kotlin
2. Ejecutar `Rebuild Project`
3. Compilar APK
4. Instalar en dispositivo
5. Probar cambios

### Para añadir características:

1. Crear nuevas clases en paquetes apropiados
2. Actualizar DAOs si es necesario
3. Actualizar layouts si es necesario
4. Añadir strings a `strings.xml`
5. Compilar y probar

## 🐛 Solución de problemas

### "No se detectan dispositivos USB"

- Verificar que el teclado/mouse esté conectado
- Intentar reconectar los dispositivos
- Verificar permisos de USB en configuración del TV Box

### "La app se congela"

- Reiniciar la aplicación
- Verificar logs: `adb logcat | grep TVBox`

### "El monitoreo no inicia"

- Verificar que la app tiene permisos necesarios
- Intentar habilitar/deshabilitar el servicio de accesibilidad
- Reiniciar el TV Box

## 📝 Licencia

Este proyecto es de código abierto. Modifica libremente según necesites.

## 👨‍💻 Desarrollo

**Lenguaje**: Kotlin  
**Patrón de arquitectura**: MVVM con Repository  
**Estado**: Versión 1.0.0 (Beta)

### Funciones planeadas:

- [ ] Interfaz gráfica mejorada para perfiles
- [ ] Exportar/importar configuraciones
- [ ] Historial de eventos
- [ ] Estadísticas de uso
- [ ] Integración con CEC (Consumer Electronics Control)
- [ ] Soporte para joysticks

## 📞 Soporte

Para reportar bugs o sugerir mejoras, utiliza el sistema de issues del proyecto.

---

**Última actualización**: Agosto 2026  
**Versión**: 1.0.0
