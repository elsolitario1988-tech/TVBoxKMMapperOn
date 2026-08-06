# Changelog

Todos los cambios notables en este proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto sigue [Semantic Versioning](https://semver.org/lang/es/).

## [1.0.0] - 2026-08-04

### ✨ Características iniciales

#### Monitoreo de entrada
- [x] Detección automática de dispositivos USB (teclado y mouse)
- [x] Monitor en tiempo real de eventos de entrada
- [x] Registro de últimas teclas presionadas
- [x] Tracking de posición del mouse (X, Y)
- [x] Detección de botones del mouse (izquierdo, derecho, centro)
- [x] Tracking de eventos de scroll (arriba/abajo)
- [x] Log de eventos con timestamp
- [x] UI optimizada para Android TV (landscape)

#### Configuración
- [x] Ajuste de sensibilidad del mouse (0.5x - 3.0x)
- [x] Control de velocidad del cursor (0.5x - 3.0x)
- [x] Control de velocidad del scroll (0.5x - 3.0x)
- [x] Guardar preferencias con DataStore
- [x] Interfaz de configuración intuitiva

#### Perfiles por aplicación
- [x] Crear perfiles específicos para cada aplicación
- [x] Base de datos Room para almacenamiento
- [x] Detección automática de app activa
- [x] Aplicación automática de perfiles
- [x] Editar y eliminar perfiles

#### Remapeo de teclas
- [x] Estructura para remapeo de teclas
- [x] Almacenamiento en base de datos
- [x] Validación de mapeos válidos
- [x] Reset a valores predeterminados

#### Servicios
- [x] Foreground Service para monitoreo continuo
- [x] Accesibilidad Service para funciones permitidas
- [x] Boot Receiver para inicio automático
- [x] USB Device Receiver para detección de dispositivos

#### UI/UX
- [x] Material Design 3 (tema oscuro)
- [x] Interfaz responsive para TV Box
- [x] Soporte para teclado y control remoto
- [x] CardView para mejor visual
- [x] Sliders para ajustes numéricos
- [x] Switches para opciones booleanas

#### Desarrollo
- [x] Proyecto modular por paquetes
- [x] Coroutines para operaciones asincrónicas
- [x] MVVM architecture ready
- [x] DAO pattern con Room
- [x] Repository pattern
- [x] Logs informativos

### 🏗️ Estructura técnica

- Kotlin 1.9.10
- Android Gradle Plugin 8.1.0
- Compile SDK 34
- Min SDK 21
- Target SDK 34
- Arquitecturas: arm64-v8a, armeabi-v7a

### 📦 Dependencias principales

- AndroidX: 1.12.0+
- Material Design 3: 1.10.0
- Android TV: 1.0.0-alpha10
- Kotlin Coroutines: 1.7.3
- Room: 2.6.0
- DataStore: 1.0.0

### 📄 Documentación

- [x] README.md - Documentación general
- [x] COMPILACION_MOVIL.md - Guía para móviles
- [x] DESCARGA_E_INSTALACION.md - Guía de inicio rápido
- [x] LIMITACIONES_Y_ALTERNATIVAS.md - Explicación de seguridad
- [x] CHANGELOG.md - Este archivo

### ⚠️ Limitaciones conocidas (versión 1.0.0)

- ❌ Remapeo global sin ROOT (requiere acceso de sistema)
- ❌ Inyección de entrada sin permisos de sistema
- ❌ Interfaz de remapeo de teclas incompleta
- ❌ Interfaz de perfiles incompleta
- ❌ No hay exportar/importar configuración

### 🐛 Bugs conocidos

- Ninguno reportado aún

### 🔄 Próximas versiones (Roadmap)

#### [1.1.0] - Próximamente
- [ ] UI mejorada para perfiles
- [ ] Interfaz completa de remapeo
- [ ] Exportar/importar configuración JSON
- [ ] Historial de eventos filtrable
- [ ] Estadísticas de uso

#### [1.2.0] - En consideración
- [ ] Soporte para joysticks
- [ ] Integración CEC (Control de TV)
- [ ] Perfiles por hora/timer
- [ ] Notificaciones personalizadas
- [ ] Widget para status

#### [2.0.0] - Futuro
- [ ] Soporte completo con ROOT (remapeo global)
- [ ] App de configuración web (para PC)
- [ ] Sincronización en la nube
- [ ] Soporte para teclados Bluetooth
- [ ] Macro de teclas
- [ ] Atajos personalizados

## Notas de desarrollo

### Cambios de arquitectura desde beta

- Migración a Kotlin Flow para observables
- Implementación de Room para persistencia
- Servicios y Receivers bien separados
- ViewBinding en lugar de findViewById
- Coroutines para operaciones async

### Decisiones de diseño

1. **Sin ROOT por defecto**: La app respeta las limitaciones de Android
2. **Material Design 3**: Tema moderno y consistente
3. **Arquitectura modular**: Fácil de mantener y expandir
4. **Room + DataStore**: Combinación optima de persistencia

### Problemas resueltos

- ✅ Memory leaks en coroutines
- ✅ Database threading issues
- ✅ Preference synchronization
- ✅ Lifecycle management de servicios

## Instalación de versiones anteriores

Si necesitas una versión específica, consulta las releases en GitHub.

```bash
# Descargar versión específica
git checkout tags/v1.0.0

# O compilar desde source
./gradlew assembleDebug
```

## Contribuciones futuras

Se aceptan pull requests. Por favor:

1. Crear fork del proyecto
2. Crear rama para tu feature: `git checkout -b feature/AmazingFeature`
3. Commit cambios: `git commit -m 'Add some AmazingFeature'`
4. Push a la rama: `git push origin feature/AmazingFeature`
5. Abrir Pull Request

## Soporte

Para reportar bugs o solicitar features:

1. Abrir Issue en GitHub
2. Describir el problema claramente
3. Incluir logs si es posible
4. Especificar dispositivo y versión de Android

## Licencia

Ver LICENSE.md para detalles.

---

## Historial de versiones por archivo

### MainActivity
- v1.0.0: Implementación inicial con monitoreo básico

### SettingsActivity
- v1.0.0: Todos los sliders y switches funcionales

### KeyRemappingActivity
- v1.0.0: Estructura lista, UI incompleta

### AppProfileActivity
- v1.0.0: Estructura lista, UI incompleta

### Services
- v1.0.0: InputMonitorService y AccessibilityService básicos

### Database
- v1.0.0: Room schema v1 stable

---

**Última actualización**: 4 de Agosto de 2026
**Versión actual**: 1.0.0 (Beta)
**Estado**: Estable para uso básico
