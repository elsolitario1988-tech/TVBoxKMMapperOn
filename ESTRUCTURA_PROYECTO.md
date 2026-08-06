# 📁 Estructura completa del proyecto TVBox KM Mapper

## 🎯 Visión general

```
TVBoxKMMapper/
├── 📄 Documentación
│   ├── README.md                          # Documentación general
│   ├── COMPILACION_MOVIL.md               # Guía para compilar desde móvil
│   ├── DESCARGA_E_INSTALACION.md          # Inicio rápido
│   ├── LIMITACIONES_Y_ALTERNATIVAS.md    # Seguridad y limitaciones
│   ├── CHANGELOG.md                       # Historial de versiones
│   ├── ESTRUCTURA_PROYECTO.md             # Este archivo
│   └── local.properties.example           # Referencia de config local
│
├── 🛠️ Configuración de Gradle
│   ├── build.gradle.kts                   # Config raíz
│   ├── settings.gradle.kts                # Módulos del proyecto
│   ├── gradle.properties                  # Propiedades globales
│   └── .gitignore                         # Archivos ignorados
│
└── 📱 Código de la aplicación (app/)
    ├── build.gradle.kts                   # Config del módulo app
    ├── proguard-rules.pro                 # Reglas de obfuscación
    │
    └── src/main/
        ├── AndroidManifest.xml            # Declaración de la app
        │
        ├── java/com/tvbox/kmmapper/
        │   ├── data/
        │   │   ├── db/
        │   │   │   └── AppDatabase.kt     # Base de datos Room
        │   │   ├── dao/
        │   │   │   └── Daos.kt            # Data Access Objects
        │   │   ├── models/
        │   │   │   └── Models.kt          # Data classes
        │   │   ├── preferences/
        │   │   │   └── PreferencesProvider.kt  # DataStore
        │   │   └── repository/
        │   │       └── InputRepository.kt # Repositorio de datos
        │   │
        │   ├── service/
        │   │   ├── InputMonitorService.kt # Foreground Service
        │   │   └── KeyRemappingAccessibilityService.kt # Accessibility
        │   │
        │   ├── receiver/
        │   │   ├── BootReceiver.kt        # Inicio automático
        │   │   └── UsbDeviceReceiver.kt   # Detección USB
        │   │
        │   └── ui/
        │       ├── MainActivity.kt         # Actividad principal
        │       ├── SettingsActivity.kt     # Configuración
        │       ├── KeyRemappingActivity.kt # Remapeo de teclas
        │       └── AppProfileActivity.kt   # Perfiles por app
        │
        └── res/
            ├── layout/
            │   ├── activity_main.xml            # Layout principal
            │   ├── activity_settings.xml        # Layout configuración
            │   ├── activity_key_remapping.xml   # Layout remapeo
            │   └── activity_app_profile.xml     # Layout perfiles
            │
            ├── values/
            │   ├── colors.xml                   # Paleta Material Design 3
            │   ├── strings.xml                  # Cadenas de texto
            │   ├── themes.xml                   # Estilos y temas
            │   └── dimens.xml                   # Dimensiones
            │
            ├── drawable/
            │   └── ic_launcher_foreground.xml   # Icono de la app
            │
            └── xml/
                ├── usb_device_filter.xml        # Filtro de dispositivos USB
                ├── accessibility_config.xml     # Config de accesibilidad
                ├── data_extraction_rules.xml    # Reglas de extracción
                └── backup_rules.xml             # Config de backup
```

## 📊 Desglose por capas

### Capa de Datos (data/)

| Archivo | Responsabilidad |
|---------|-----------------|
| **Models.kt** | Define estructuras de datos (data classes) |
| **Daos.kt** | Interfaz de acceso a base de datos |
| **AppDatabase.kt** | Configuración de Room Database |
| **InputRepository.kt** | Abstracción de acceso a datos |
| **PreferencesProvider.kt** | Manejo de preferencias globales |

**Patrones**: Repository, DAO, Data Transfer Object

### Capa de Servicios y Receivers (service/ + receiver/)

| Archivo | Responsabilidad |
|---------|-----------------|
| **InputMonitorService.kt** | Monitoreo continuo en foreground |
| **KeyRemappingAccessibilityService.kt** | Integración con accesibilidad |
| **BootReceiver.kt** | Inicio automático en boot |
| **UsbDeviceReceiver.kt** | Detección de dispositivos USB |

**Patrones**: Service, Broadcast Receiver, Lifecycle management

### Capa de UI (ui/)

| Archivo | Responsabilidad |
|---------|-----------------|
| **MainActivity.kt** | Pantalla principal con monitor |
| **SettingsActivity.kt** | Configuración de la app |
| **KeyRemappingActivity.kt** | Interfaz de remapeo |
| **AppProfileActivity.kt** | Gestión de perfiles |

**Patrones**: MVVM ready, ViewModel (preparado para expansión)

### Capa de Recursos (res/)

| Carpeta | Contenido |
|---------|-----------|
| **layout/** | 4 archivos XML de interfaces |
| **values/** | Strings, colores, temas, dimensiones |
| **drawable/** | Vectores SVG y drawables |
| **xml/** | Configuración de permisos y servicios |

## 🔄 Flujo de datos

```
┌─────────────────────────────────────────────────┐
│           CAPA DE PRESENTACIÓN (UI)              │
│  ┌─────────────────────────────────────────┐   │
│  │ Activities (MainActivity, Settings, etc)│   │
│  │ - Mostrar datos                         │   │
│  │ - Capturar interacción del usuario      │   │
│  └──────────────┬──────────────────────────┘   │
└─────────────────┼──────────────────────────────┘
                  │ LifecycleScope.launch {}
                  ↓
┌─────────────────────────────────────────────────┐
│         CAPA DE LÓGICA DE NEGOCIO               │
│  ┌─────────────────────────────────────────┐   │
│  │ Repository (InputRepository)             │   │
│  │ - Orquestar operaciones                  │   │
│  │ - Coordinar DAOs y Preferences           │   │
│  └──────────────┬──────────────────────────┘   │
└─────────────────┼──────────────────────────────┘
                  │ dao.update(), preferences.set()
                  ↓
┌─────────────────────────────────────────────────┐
│         CAPA DE PERSISTENCIA                    │
│  ┌──────────────────────┐ ┌──────────────────┐ │
│  │ Room Database        │ │ DataStore        │ │
│  │ (KeyMapping,Profile) │ │ (Preferencias)   │ │
│  └──────────────────────┘ └──────────────────┘ │
│           SQLite                  Protobuf      │
└─────────────────────────────────────────────────┘
```

## 🔌 Integración de componentes

```
EVENTOS EXTERNOS
      ↓
┌─────────────────┐
│ Boot Receiver   │ ← BOOT_COMPLETED
│ USB Receiver    │ ← USB_DEVICE_ATTACHED
└────────┬────────┘
         │
         ↓
┌──────────────────────────┐
│ InputMonitorService      │
│ (Foreground Service)     │
└────────┬─────────────────┘
         │
         ↓
    Data Layer
         │
         ↓
┌──────────────────────────┐
│ MainActivity             │
│ - Muestra eventos        │
│ - Inicia/detiene service │
└──────────────────────────┘
    ↓
┌──────────────────────────┐
│ SettingsActivity         │
│ KeyRemappingActivity     │
│ AppProfileActivity       │
└──────────────────────────┘
```

## 📦 Dependencias externas

### Google/AndroidX
- `androidx.core:core-ktx` - Extensiones Kotlin para Android
- `androidx.appcompat:appcompat` - Compatibilidad con versiones antiguas
- `androidx.lifecycle` - Management del ciclo de vida
- `androidx.room` - Persistencia con SQLite
- `androidx.datastore` - Almacenamiento de preferencias

### Material Design
- `com.google.android.material:material` - Componentes Material Design 3
- `androidx.tv:tv-foundation` - Soporte específico para Android TV
- `androidx.leanback` - Interfaz optimizada para TV

### Kotlin/Coroutines
- `kotlinx.coroutines` - Programación asincrónica
- `org.jetbrains.kotlin` - Compilador Kotlin

### Otros
- `com.google.code.gson:gson` - Serialización JSON (preparado)

## 🎯 Responsabilidades por paquete

### com.tvbox.kmmapper
Raíz del proyecto

### com.tvbox.kmmapper.data
- **db**: Definición y configuración de base de datos
- **dao**: Interfaces CRUD para entidades
- **models**: Classes de datos que representan dominio
- **preferences**: Acceso a datos simples/globales
- **repository**: Abstracción de acceso a datos

### com.tvbox.kmmapper.service
Servicios que corren en background
- Foreground Service para monitoreo
- Accessibility Service para integración

### com.tvbox.kmmapper.receiver
Componentes que reaccionan a eventos del sistema
- Boot Receiver para inicio automático
- USB Receiver para detección de dispositivos

### com.tvbox.kmmapper.ui
Todas las pantallas (Activities)
- Enlace con data layer vía coroutines
- Actualización de UI
- Manejo de eventos del usuario

## 📱 Recursos

### Strings (values/strings.xml)
- 50+ cadenas de texto
- Títulos, etiquetas, botones, mensajes
- Separación de idioma para futura i18n

### Colors (values/colors.xml)
- Paleta Material Design 3 completa
- Colores de estado (conectado, desconectado)
- Colores de UI (accent, error, surface)

### Themes (values/themes.xml)
- Tema base Material Components
- Estilos personalizados para componentes
- Tipografía coordinada
- Colores aplicados consistentemente

### Dimens (values/dimens.xml)
- Tamaños de texto estandarizados
- Espaciado consistente
- Radios de esquina
- Elevaciones

## 🔐 Permisos declarados

Ver `AndroidManifest.xml`:

```xml
<!-- Entrada de dispositivos -->
<uses-permission android:name="android.permission.OBSERVE_GRANT_REVEAL_PERMISSIONS" />

<!-- Inicio automático -->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<!-- Servicio en primer plano -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- Efectos de vibración -->
<uses-permission android:name="android.permission.VIBRATE" />

<!-- Accesibilidad (opcional) -->
<uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
```

## 🔧 Archivos de configuración

| Archivo | Propósito |
|---------|-----------|
| **build.gradle.kts** | Versiones de herramientas y dependencias |
| **settings.gradle.kts** | Módulos del proyecto |
| **gradle.properties** | Propiedades globales de Gradle |
| **proguard-rules.pro** | Reglas de ofuscación para release |

## 📋 Archivos AndroidManifest.xml

Declara:
- Actividades (4)
- Servicios (2)
- Broadcast Receivers (2)
- Permisos (8+)
- Features de hardware (opcionales)

## 🏗️ Arquitectura general

```
┌────────────────────────────────────────────────┐
│         PRESENTACIÓN (UI Layer)                │
│  Activities + Layouts + Resources              │
└─────────────────────┬──────────────────────────┘
                      │ Coroutines + Flow
                      ↓
┌────────────────────────────────────────────────┐
│      LÓGICA DE NEGOCIO (Domain Layer)          │
│  Servicios + Receivers + Repository            │
└─────────────────────┬──────────────────────────┘
                      │ DAO Queries
                      ↓
┌────────────────────────────────────────────────┐
│       PERSISTENCIA (Data Layer)                │
│  Room (SQLite) + DataStore (Protobuf)         │
└────────────────────────────────────────────────┘
```

## 📈 Escalabilidad futura

El proyecto está estructurado para crecer:

```
Versión 1.0 (Beta)
├── Core funcional
├── Arquitectura modular
└── Base lista para expansión
       ↓
Versión 1.1
├── ViewModels (MVVM completo)
├── LiveData/StateFlow
└── Tests unitarios
       ↓
Versión 1.2+
├── Features adicionales
├── Módulos independientes
└── Posible plugin architecture
```

## 🧪 Testing (preparado)

La estructura permite añadir fácilmente:
- **Unit tests** (JUnit + Mockito)
- **Integration tests** (Espresso)
- **DAO tests** (Room + coroutines)

## ✅ Checklist de compilación

Para verificar que todo está en orden:

```
✅ build.gradle.kts - Versiones correctas
✅ AndroidManifest.xml - Permisos completos
✅ Todos los layouts - Sin errores XML
✅ Strings - No hay hardcoding
✅ Colores - Material Design 3
✅ Actividades - Compilan sin errores
✅ Servicios - Implementados
✅ Receivers - Registrados
✅ Database - Entities y DAOs
✅ Repository - Métodos implementados
```

## 📝 Notas de mantenimiento

- **Gradle**: Actualizar a versiones LTS
- **Kotlin**: Mantener 1.9.x+
- **AndroidX**: Actualizar regularmente
- **Material Design**: Seguir directrices oficiales
- **Testing**: Agregar cobertura progresivamente

---

**Última actualización**: Agosto 2026
**Versión**: 1.0.0
**Estado**: Listo para compilación y distribución
