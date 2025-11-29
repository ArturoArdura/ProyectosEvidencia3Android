# Portafolio de Desarrollo Android - Rick & Morty "Neon Perks"
**Autor:** Arturo Ardura Palacios
**Curso:** 6to Semestre - Apps Android

---

## ⚠️ NOTA IMPORTANTE PARA EL PROFESOR ⚠️

> **"Profe se me olvidó que tendria que subirlos por separados al final y sobrescribí cada actividad con la anterior al seguir el desarrollo, por favor tenga piedaaaad, de todas formas le puse en cada carpeta el APK para que no se vea tan vacio."**

---

## 📝 Bitácora de Evolución del Proyecto

Este repositorio contiene el código fuente de la versión final (Actividad 12), pero representa la culminación de un trabajo continuo a lo largo de las últimas semanas. A continuación, describo mi experiencia y los cambios realizados en cada etapa:

### 🔹 Actividad 9: Primeros Pasos (Icono Personalizado)
**Estado:** *Inicial*
En esta etapa, el proyecto era un lienzo en blanco. Todavía no existía la temática de la serie.
- **Experiencia:** Me enfoqué en entender la estructura básica de un proyecto en Android Studio.
- **Cambio Principal:** Configuración del **ícono del launcher** personalizado (antes de que todo se volviera "sci-fi"). Fue mi primer contacto real manipulando los recursos (`res`) del proyecto.

### 🔹 Actividad 10: Rick & Morty (Versión "Chafa")
**Estado:** *Tematización UI*
Aquí fue donde decidí darle la identidad de **Rick y Morty**.
- **Experiencia:** Transformé la aplicación visualmente. Admito que al principio se veía algo "chafa" (básica) porque estaba aprendiendo a usar los Layouts y las imágenes.
- **Cambio Principal:** Implementación de imágenes estáticas, colores neón y la estructura visual básica de lo que hoy es la pantalla de "Home". Aún no había lógica compleja, solo diseño.

### 🔹 Actividad 11: Conexión a Supabase (El Cerebro)
**Estado:** *Integración Backend (CRUD)*
El salto técnico más grande. Dejó de ser una app estática para tener "memoria".
- **Experiencia:** Fue un reto conectar la app a la nube. Aprendí a usar librerías externas para red y bases de datos.
- **Cambio Principal:** Integración de **Supabase**. Implementé el CRUD para poder leer datos reales en lugar de tenerlos "hardcodeados" en la app. Aquí nacieron los `ViewModels` y la gestión de estados para cargar la información.

### 🔹 Actividad 12: Proyecto Final (La Pulida)
**Estado:** *Final (Código actual del repositorio)*
Lo que ves hoy en el código (`HomeFragment.kt`, `Adapters`, etc.).
- **Experiencia:** Hoy terminamos de integrar todo. Añadí las animaciones y la lógica de "negocio" (Bounties, Rewards).
- **Cambio Principal:** 
    - Uso de **ObjectAnimator** para la animación del portal giratorio (rotación suave e interpoladores).
    - Lógica completa de `BountyAdapter` y `RewardAdapter` usando **Glide** para cargar imágenes de los personajes/ítems.
    - Manejo de `ViewBinding` para un código más limpio.
    - Feedback al usuario (Toasts y Diálogos de "Dimension Reached").

---

## 🛠 Tech Stack Utilizado (Versión Final)
- **Lenguaje:** Kotlin
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Backend:** Supabase (PostgreSQL & Auth)
- **Librerías Clave:**
  - `Glide` (Carga de imágenes)
  - `Retrofit / Ktor` (Networking)
  - `ViewBinding`
  - `Coroutines` (Manejo asíncrono)

---

### 📂 Estructura de Entregables
Aunque el código fuente refleja la versión final (Act 12), he organizado las carpetas de entrega con los **APKs compilados** de cada etapa para evidenciar el progreso histórico del modulo.
