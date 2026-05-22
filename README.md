# 🔴 Redscate – Radar de Rescatistas en Emergencias

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Connectivity-Wi--Fi%20Direct-0078D4?style=for-the-badge&logo=wifi&logoColor=white"/>
  <img src="https://img.shields.io/badge/Android-10%2B-green?style=for-the-badge&logo=android"/>
  <img src="https://img.shields.io/badge/Distinción-Tesis%20Meritoria%20%2F%20Laureada-FFD700?style=for-the-badge"/>
</p>

> **"Tu seguridad es nuestra prioridad"** — Aplicación móvil Android para soporte en situaciones de emergencia, sin necesidad de internet ni señal celular.

---

## 📖 ¿Qué es Redscate?

**Redscate** es una aplicación móvil Android diseñada para brindar soporte en situaciones de emergencia, especialmente **incendios**, donde las redes de telecomunicaciones convencionales pueden estar fuera de servicio.

La app resuelve dos problemas críticos en situaciones de desastre:

1. **Localización sin internet:** Mediante un radar en tiempo real con **Wi-Fi Direct (red ad-hoc)**, los sobrevivientes pueden ser detectados por rescatistas cercanos sin señal celular ni conexión a internet.
2. **Desorientación en emergencias:** Mediante tarjetas de acción rápida y una biblioteca educativa, los usuarios saben exactamente qué hacer antes y durante una emergencia.

> 💡 El diseño de la app fue validado con miembros de la **Cruz Roja**, quienes aportaron requerimientos reales sobre atención a sobrevivientes.

---

## 🎯 Dos roles de usuario

### 🟥 Rol Sobreviviente
- Activa el **radar S.O.S** para emitir su posición y ser detectado por rescatistas
- Visualiza en el radar anillos de distancia (200m → 150m → 100m → 50m) para saber qué tan cerca está la ayuda
- Recibe alerta sonora automática al máximo volumen cuando un rescatista está a menos de **50 metros**
- Accede a **tarjetas de autoprotección** con pasos visuales claros mientras espera
- Puede registrar datos personales (nombre, edad, tipo de sangre, contacto de emergencia)

### 🟩 Rol Rescatista
- Activa el **radar de búsqueda** para detectar sobrevivientes cercanos
- Visualiza nombre e identidad del sobreviviente encontrado en pantalla
- Monitorea la distancia en tiempo real mediante anillos concéntricos

---

## ✨ Módulos de la aplicación

| Módulo | Rol | Descripción |
|--------|-----|-------------|
| 📡 **Radar** | Ambos | Detección en tiempo real vía Wi-Fi Direct, sin internet |
| 📚 **Recursos** | Sobreviviente | Guías de prevención: reacción en campo, ciudad, hogar y trabajo |
| 🛡️ **Autoprotección** | Sobreviviente | Tarjetas paso a paso para actuar durante la emergencia |
| 🧰 **Kit de emergencia** | Sobreviviente | Lista de elementos esenciales para estar preparado |
| 🧭 **Brújula** | Ambos | Orientación hacia el norte usando sensores del dispositivo |
| 👤 **Perfil** | Ambos | Datos personales y contacto de emergencia del usuario |

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|-----------|-----|
| **Kotlin** | Lenguaje principal de desarrollo |
| **Android Studio** | Entorno de desarrollo (via JetBrains Toolbox) |
| **Wi-Fi Direct API** | Comunicación P2P entre dispositivos sin internet |
| **GPS / LocationManager** | Coordenadas de latitud y longitud en tiempo real |
| **Magnetómetro + Acelerómetro** | Brújula de orientación al norte geográfico |
| **Android SDK (API 29+)** | Base del desarrollo — Android 10 o superior |
| **Persistencia de datos** | Almacenamiento local de perfil del usuario |

---

## 📦 Paquete de datos Wi-Fi Direct

Cada paquete de datos enviado entre dispositivos contiene:

```
{
  rol: "sobreviviente" | "rescatista",
  latitud: Double,
  longitud: Double,
  nombre: String
}
```

---

## 📐 Arquitectura del sistema

```
┌──────────────────────────────────────────────────────────┐
│                  DISPOSITIVO SOBREVIVIENTE                │
│  ┌──────────────┐   ┌───────────────┐  ┌──────────────┐  │
│  │  Radar UI    │   │  Wi-Fi Direct │  │ GPS + Brújula│  │
│  │  (S.O.S)     │◄─►│  P2P Manager  │  │ Sensor Mgr   │  │
│  └──────────────┘   └──────┬────────┘  └──────────────┘  │
└─────────────────────────────┼────────────────────────────┘
                              │  Red Ad-Hoc · Sin internet
                              │  Wi-Fi Direct (hasta ~200m)
┌─────────────────────────────┼────────────────────────────┐
│                  DISPOSITIVO RESCATISTA                   │
│  ┌──────────────┐   ┌───────▼────────┐  ┌──────────────┐ │
│  │  Radar UI    │   │  Wi-Fi Direct  │  │ GPS + Brújula│ │
│  │  (BUSCAR)    │◄─►│  P2P Manager   │  │ Sensor Mgr   │ │
│  └──────────────┘   └────────────────┘  └──────────────┘ │
└──────────────────────────────────────────────────────────┘
```

---

## 📱 Screenshots

| Inicio | Radar activo | Rescatista encontrado |
|--------|-------------|----------------------|
| ![inicio](docs/screenshots/inicio.png) | ![radar](docs/screenshots/radar_sos.png) | ![encontrado](docs/screenshots/rescatista_encontrado.png) |

| Recursos | Kit de emergencia | Autoprotección |
|----------|-----------------|----------------|
| ![recursos](docs/screenshots/recursos.png) | ![kit](docs/screenshots/kit.png) | ![cards](docs/screenshots/autoproteccion.png) |

> 📸 *Agrega tus capturas en la carpeta `docs/screenshots/`*

---

## 🚀 Instalación

### Requisitos del dispositivo

| Requisito | Especificación |
|-----------|---------------|
| Sistema operativo | Android 10 (API 29) o superior |
| Wi-Fi | Sensor Wi-Fi requerido para Wi-Fi Direct |
| Brújula | Magnetómetro integrado |
| GPS | Sensor de ubicación (latitud/longitud) |
| Almacenamiento | Mínimo 20 MB libres |

> ⚠️ **Nota:** Ambos dispositivos deben tener instalada la app, con **Wi-Fi y ubicación activados** antes de iniciar.

### Opción A — Instalar APK directamente

1. Descarga el archivo `Redscate.apk` desde la sección [Releases](../../releases)
2. En tu dispositivo, ve a **Mis archivos → Descargas**
3. Abre el APK y sigue los pasos de instalación
4. Si Google Play Protect lo solicita, selecciona **"Analizar app"** y luego **"Instalar"**

### Opción B — Compilar desde Android Studio

```bash
# 1. Clona el repositorio
git clone https://github.com/ernidev01/redscate.git

# 2. Abre el proyecto en Android Studio

# 3. Sincroniza las dependencias con Gradle

# 4. Activa el modo desarrollador en tu dispositivo:
#    Ajustes → Acerca del teléfono → Información del software
#    → Toca "Número de compilación" 5 veces

# 5. Activa "Depuración por USB" en Opciones de desarrollador

# 6. Conecta tu dispositivo por USB y presiona "Run" en Android Studio
```

> ✅ La compilación es exitosa cuando aparece **"Install successfully"** en la consola de Android Studio.

### Permisos requeridos

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.NEARBY_WIFI_DEVICES"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
```

---

## ❗ Solución de problemas

| Problema | Solución |
|---------|----------|
| El radar no detecta dispositivos | Verifica que Wi-Fi y ubicación estén activados en ambos dispositivos |
| La brújula apunta de forma errática | Mantén el dispositivo en **posición horizontal** para mejor precisión |
| Permisos denegados | Ve a **Ajustes → Aplicaciones → Redscate → Permisos** y actívalos manualmente, luego reinicia la app |
| Solo funciona el módulo Recursos | Wi-Fi o ubicación están desactivados; actívalos para usar el radar |

---

## 🏆 Reconocimiento académico

Este proyecto fue desarrollado como **Trabajo de Grado** en la **Universidad Antonio Nariño** (2024–2025) y fue reconocido con la distinción de:

> **🥇 Tesis Meritoria / Laureada**
>
> Distinción otorgada por su innovación técnica, impacto social y calidad en el desarrollo.
>
> Registro Profesional: **091132-0794602 CNDC**

---

## 👨‍💻 Autor

**Erick Nicolás González Rojas**  
Ingeniero de Software · Universidad Antonio Nariño · Grado Cum Laude

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Erick%20González-0077B5?style=flat&logo=linkedin)](https://www.linkedin.com/in/erick-nicolas-gonzalez-rojas/)
[![GitHub](https://img.shields.io/badge/GitHub-ernidev01-181717?style=flat&logo=github)](https://github.com/ernidev01)
[![Email](https://img.shields.io/badge/Email-erniDev012%40gmail.com-D14836?style=flat&logo=gmail)](mailto:erniDev012@gmail.com)

---

## 📄 Licencia

Este proyecto fue desarrollado en el marco de un trabajo de grado académico. Para uso, colaboración o distribución, por favor contactar al autor directamente.

---

<p align="center">
  Desarrollado con ❤️ para salvar vidas · Bogotá, Colombia · 2025
</p>

