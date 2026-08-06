# Guía de Compilación en Dispositivo Móvil

Esta guía te ayudará a compilar TVBox KM Mapper desde un dispositivo móvil usando herramientas como CodeAssist, AIDE, o AndroidIDE.

## 📱 Requisitos Previos

- Dispositivo Android con al menos 2GB RAM libre
- Android 8.0 o superior
- Una de estas herramientas:
  - **CodeAssist** (recomendado)
  - **AIDE**
  - **AndroidIDE**
  - **Termux + Gradle**

## 🚀 Compilación con CodeAssist

### Pasos:

1. **Descargar el proyecto**
   ```bash
   # Opción 1: Clonar desde ZIP
   - Descargar TVBoxKMMapper.zip
   - Extraer en /sdcard/Download/TVBoxKMMapper
   
   # Opción 2: Usar git (si está disponible)
   git clone https://github.com/tu-repo/TVBoxKMMapper.git
   ```

2. **Abrir en CodeAssist**
   - Abrir CodeAssist
   - `File > Open`
   - Navegar a `/sdcard/Download/TVBoxKMMapper`
   - Seleccionar la carpeta `app`
   - Esperar a que cargue la estructura

3. **Sincronizar Gradle**
   - CodeAssist sincronizará automáticamente
   - Esperar a que descargue todas las dependencias
   - Esto puede tomar 5-10 minutos

4. **Compilar**
   - Opción A: `Build > Compile` (para verificación)
   - Opción B: `Build > Build APK` (para generar APK Debug)
   - Opción C: `Build > Build APK Release` (necesita keystore)

5. **APK generado**
   - El APK estará en: `app/build/outputs/apk/debug/app-debug.apk`
   - Transferir a `/sdcard/Documents/` para instalar

## 🔧 Compilación con AIDE

### Pasos:

1. **Crear nuevo proyecto**
   - Abrir AIDE
   - `New > Import Project`
   - Seleccionar carpeta del proyecto

2. **Configurar**
   - AIDE debería detectar que es un proyecto Gradle
   - Permitir descargar dependencias
   - Esperar a sincronización

3. **Compilar**
   - Menu > Build > Build
   - Seleccionar "APK" si hay opción
   - Esperar a que finalice

4. **Resultado**
   - El APK se guardará en la carpeta del proyecto
   - Instalarlo desde el gestor de archivos

## 🐛 Solución de problemas en móvil

### Error: "No se encuentra el SDK"
```
Solución:
- En CodeAssist: Settings > SDK > Seleccionar SDK correcto
- En AIDE: Configurar path del SDK manualmente
```

### Error: "Memoria insuficiente"
```
Solución:
- Cerrar otras aplicaciones
- Borrar caché: Ajustes > Apps > [CodeAssist/AIDE] > Almacenamiento > Borrar caché
- Aumentar memoria de la JVM en gradle.properties
```

### Error: "Demasiadas dependencias"
```
Solución:
- Esperar más tiempo (puede tomar 30+ minutos)
- O compilar desde PC
```

### Error: "Conexión de red perdida"
```
Solución:
- Usar WiFi (no datos móviles)
- Reintentar sincronización
- O descargar dependencias desde PC y transferir
```

## 📦 Compilación con EAS Build (Alternativa en nube)

Si tienes problemas compilando localmente, puedes usar Replit + EAS Build:

1. **Crear cuenta en Replit**
   - Ir a https://replit.com
   - Sign up con GitHub/Google

2. **Crear nuevo Replit**
   - `New Replit`
   - Seleccionar "Android"
   - Copiar código del proyecto

3. **Compilar en la nube**
   ```bash
   # En la terminal de Replit
   cd TVBoxKMMapper
   ./gradlew assembleDebug
   # El APK se generará en app/build/outputs/apk/debug/
   ```

4. **Descargar APK**
   - Files > Navegar a `app/build/outputs/apk/debug/`
   - Descargar `app-debug.apk`

## 🔑 Firmar APK para Release (Opcional)

### Generar keystore:

```bash
# En CodeAssist/Terminal
keytool -genkey -v -keystore release.keystore \
  -keyalg RSA -keysize 2048 -validity 36500 \
  -alias tvbox_kmmapper
```

### Configurar en build.gradle.kts:

```kotlin
signingConfigs {
    release {
        storeFile = file("release.keystore")
        storePassword = "tu_password"
        keyAlias = "tvbox_kmmapper"
        keyPassword = "tu_password"
    }
}

buildTypes {
    release {
        signingConfig = signingConfigs.getByName("release")
    }
}
```

### Compilar Release:

```bash
./gradlew assembleRelease
# APK en: app/build/outputs/apk/release/app-release.apk
```

## 📲 Instalar APK

### Método 1: Desde el gestor de archivos
1. Localizar el APK
2. Tocar para instalar
3. Permitir instalación de fuentes desconocidas si es necesario

### Método 2: Usando adb desde PC
```bash
adb connect TV_BOX_IP
adb install -r app-debug.apk
```

### Método 3: Usando Replit/Terminal
```bash
am start -I com.tvbox.kmmapper/.ui.MainActivity
```

## ⏱️ Tiempos estimados de compilación

| Herramienta | Primera compilación | Compilaciones siguientes |
|-------------|-------------------|-------------------------|
| CodeAssist  | 20-30 minutos     | 5-10 minutos            |
| AIDE        | 25-35 minutos     | 8-12 minutos            |
| AndroidIDE  | 15-25 minutos     | 4-8 minutos             |
| Replit+EAS  | 10-15 minutos     | 3-5 minutos             |

## 💡 Consejos

1. **Primera compilación**: Hazlo durante la noche
2. **WiFi**: Siempre usa WiFi para descargar dependencias
3. **Espacio**: Asegúrate de tener 2GB libres
4. **RAM**: 3GB mínimo disponible
5. **Caché**: Limpia regularmente el caché de Gradle

## 🔄 Flujo rápido de desarrollo

```bash
# Para cambios rápidos durante desarrollo:

1. Modificar código en CodeAssist
2. `Build > Compile` (para verificar sintaxis)
3. `Build > Build APK`
4. Transferir APK a TV Box
5. Instalar: `adb install -r app-debug.apk`
6. Probar cambios

# Repetir desde paso 1
```

## 📝 Notas importantes

- **Arquitecturas**: El proyecto soporta arm64-v8a y armeabi-v7a
- **Min SDK**: 21 (Android 5.0) - Verifica la versión de tu dispositivo
- **Target SDK**: 34 (Android 14) - Compatible con Android 8.0+
- **Dependencias**: Se descargarán automáticamente (~500MB)

## ❓ Preguntas frecuentes

**P: ¿Puedo compilar desde Termux?**
R: Sí, pero necesitas instalar JDK y Gradle manualmente. Más complejo.

**P: ¿Qué pasa si falla a mitad?**
R: Ejecuta `./gradlew clean` y vuelve a intentar.

**P: ¿Puedo compilar en otro dispositivo y transferir?**
R: Sí, pero debe tener la misma arquitectura (armv8 o armv7).

**P: ¿Cómo actualizo el código?**
R: Descarga la nueva versión, reemplaza los archivos y recompila.

---

**Última actualización**: Agosto 2026  
**Versión**: 1.0.0
