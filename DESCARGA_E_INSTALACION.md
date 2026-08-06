# 🚀 Descarga e Instalación Rápida

Guía paso a paso para descargar, compilar e instalar TVBox KM Mapper.

## 📥 Opción 1: Descargar APK precompilado (Más rápido)

Si solo quieres instalar la app sin compilar:

1. **Descargar APK**
   - Descargar `app-debug.apk` o `app-release.apk`
   - Guardar en tu PC o transferir al TV Box

2. **Instalar con adb**
   ```bash
   adb connect <IP_DEL_TVBOX>
   adb install -r app-debug.apk
   ```

3. **O instalar manualmente**
   - Copiar APK a `/sdcard/Download/`
   - Abrir con cualquier aplicación instalador
   - Permitir instalación desde fuentes desconocidas si es necesario

## 🛠️ Opción 2: Compilar el código completo (Recomendado)

### Requisitos:
- Android Studio (versión 2024.1 o superior)
- JDK 11 o superior
- Mínimo 4GB RAM
- 2GB espacio en disco

### Pasos:

#### 1️⃣ Descargar el proyecto

```bash
# Opción A: Clonar con Git
git clone https://github.com/tu-usuario/TVBoxKMMapper.git
cd TVBoxKMMapper

# Opción B: Descargar ZIP
# - Ir a GitHub
# - Click en "Code" > "Download ZIP"
# - Extraer TVBoxKMMapper-main.zip
# - Navegar a la carpeta extraída
```

#### 2️⃣ Abrir en Android Studio

1. Abrir Android Studio
2. `File` → `Open`
3. Seleccionar la carpeta `TVBoxKMMapper`
4. Esperar a que indexe (2-3 minutos)
5. Dejar que descargue Gradle y dependencias (5-10 minutos)

#### 3️⃣ Compilar el APK Debug

1. En la barra superior: `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
2. O usar el atajo: `Ctrl+F9` (Windows) o `Cmd+F9` (Mac)
3. Esperar a que termine la compilación (5-10 minutos)
4. Verás un popup indicando "Build successful"

#### 4️⃣ Ubicación del APK

El APK compilado está en:
```
TVBoxKMMapper/app/build/outputs/apk/debug/app-debug.apk
```

#### 5️⃣ Instalar en TV Box

**Opción A: Con adb (si tienes adb instalado)**
```bash
adb connect <IP_DEL_TVBOX>:5555
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

**Opción B: Manual**
1. Copiar `app-debug.apk` al TV Box
2. Abrir con un instalador de APK
3. Permitir instalación

## 📱 Opción 3: Compilar desde móvil (CodeAssist/AIDE)

Ver archivo `COMPILACION_MOVIL.md` para instrucciones detalladas.

Resumen rápido:
1. Instalar CodeAssist o AIDE
2. Descargar proyecto ZIP
3. Abrir en la app
4. `Build` → `Build APK`
5. Instalar el APK resultante

## ✅ Verificar instalación

Una vez instalada, verifica que funciona:

1. Abrir la aplicación desde el drawer
2. Debería mostrar "Monitor de Entrada"
3. Conectar teclado/mouse USB
4. Deberían aparecer conectados

## 🎮 Primeros pasos

### Configuración inicial:

1. **Abrir Configuración** (botón ⚙️)
   - Habilitar "Iniciar automáticamente"
   - Ajustar sensibilidad del mouse si es necesario
   - Habilitar notificaciones

2. **Conectar dispositivos**
   - Conectar teclado USB
   - Conectar mouse USB
   - La app debería detectarlos automáticamente

3. **Iniciar monitoreo**
   - En la pantalla principal
   - Click en "Iniciar Monitoreo"
   - Presionar una tecla en el teclado
   - Debería aparecer en el log

### Crear primer perfil:

1. En TV Box, abrir Settings
2. Ir a Accesibilidad
3. Servicios > Activar "TVBox KM Mapper"
4. Volver a la app
5. Crear perfil para tu aplicación favorita

## 🐛 Solución de problemas rápida

| Problema | Solución |
|----------|----------|
| "Build failed" | Limpiar: `Build` → `Clean Project`, luego compilar de nuevo |
| "Gradle sync failed" | Eliminar `.gradle/` folder y reintentar |
| "Insufficient space" | Liberar 2GB+ en el dispositivo |
| "Device not recognized" | Instalar drivers ADB, o usar transferencia manual |
| "App no inicia" | Desinstalar y reinstalar: `adb uninstall com.tvbox.kmmapper` |

## 📋 Checklist de instalación

- [ ] Proyecto descargado
- [ ] Android Studio abierto
- [ ] Gradle sincronizado
- [ ] APK compilado
- [ ] APK instalado en TV Box
- [ ] App abre correctamente
- [ ] Dispositivos USB detectados
- [ ] Monitoreo funciona

## 📝 Configurar para compilación Release

Para distribuir versión release firmada:

1. Generar keystore:
   ```bash
   keytool -genkey -v -keystore release.keystore \
     -keyalg RSA -keysize 2048 -validity 36500 \
     -alias tvbox_kmmapper
   ```

2. Configurar en `app/build.gradle.kts`:
   ```kotlin
   signingConfigs {
       release {
           storeFile = file("release.keystore")
           storePassword = "TU_PASSWORD"
           keyAlias = "tvbox_kmmapper"
           keyPassword = "TU_PASSWORD"
       }
   }
   ```

3. Compilar:
   ```bash
   ./gradlew assembleRelease
   ```

4. APK resultante:
   ```
   app/build/outputs/apk/release/app-release.apk
   ```

## 🔄 Actualizar código

Si descargaste el proyecto hace un tiempo:

1. En Android Studio: `File` → `Sync with File System`
2. O abrir terminal en la carpeta y:
   ```bash
   git pull origin main
   ```
3. Compilar de nuevo

## 📞 Ayuda

Si encuentras problemas:

1. Revisa el archivo `README.md`
2. Revisa el archivo `COMPILACION_MOVIL.md` (si compilas desde móvil)
3. Busca en la sección de Issues del proyecto
4. Crea un nuevo Issue si no encuentras solución

## ✨ Próximos pasos

Una vez instalada:

1. Explorar la interfaz
2. Crear perfiles para tus apps favoritas
3. Remapear teclas según necesites
4. Ajustar sensibilidades a tu gusto
5. Activar inicio automático

---

**¿Necesitas ayuda?** Revisa `README.md` para documentación completa.

**Última actualización**: Agosto 2026  
**Versión**: 1.0.0
