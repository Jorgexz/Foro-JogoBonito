# Proyecto: Foro JogaBonito 

## 1. Integrantes
* **Nombre:** Jorge Alfaro y Hugo Riquelme
* **Sección:** DSY1105

## 2. Descripción, Funcionalidades y Tecnologias Usadas
Aplicación móvil nativa (Kotlin + Jetpack Compose) para un foro de fútbol, integrada con Backend propio en AWS y API externa.

**Funcionalidades Principales:**
* **Arquitectura MVVM:** Separación clara entre UI (`View`), Lógica (`ViewModel`) y Datos (`Model/Repository`).
* * **Jetpack Compose:** Interfaz de usuario declarativa y moderna.
* **Autenticación:** Login y Registro de usuarios contra base de datos propia (MySQL)
* **Gestión de Posts (CRUD Completo):** Crear, Leer, Editar y Eliminar publicaciones.
* **Seguridad:** Los usuarios solo pueden editar/borrar sus propios posts, mientras que un administrador puede borrar cualquier post.
* **Consumo de API Externa:** Integración con **TheSportsDB** para obtener datos de la Premier League.
* **Persistencia:** Base de datos MySQL en AWS EC2.

### Backend & Microservicios (AWS)
* **Servidor Node.js:** API RESTful construida con Express, modularizada en rutas (`routes_usuarios`, `routes_posts`).
* **Base de Datos MySQL:** Persistencia de datos relacional alojada en la nube.
* **Roles y Permisos :** Sistema inteligente de roles **Admin/User**.
    * *User:* Puede crear, editar y borrar sus propios posts.
    * *Admin:* Tiene permisos de moderación para **eliminar cualquier publicación** del foro.
* **Despliegue:** Instance EC2 en AWS corriendo Ubuntu con gestor de procesos `pm2`.


## Tecnologías Utilizadas

### Frontend (Android)
* **Lenguaje:** Kotlin
* **UI Toolkit:** Jetpack Compose
* **Networking:** Retrofit 2 + Gson
* **Concurrencia:** Coroutines & Flow
* **Testing:** JUnit 4 + MockK
* **Navegación:** Jetpack Navigation Compose

### Backend (Server)
* **Runtime:** Node.js
* **Framework:** Express.js
* **Base de Datos:** MySQL
* **Hosting:** AWS EC2 (Ubuntu Linux)


## 3. Endpoints Utilizados

### A. Microservicio Propio (AWS - Node.js)
* `POST /usuarios/registro` - Crear cuenta.
* `POST /usuarios/login` - Iniciar sesión.
* `GET /posts` - Obtener lista de publicaciones.
* `POST /posts` - Crear nueva publicación.
* `PUT /posts/:id` - Editar publicación existente.
* `DELETE /posts/:id` - Eliminar publicación.

### B. API Externa (TheSportsDB)
* `GET /api/v1/json/3/eventsnextleague.php?id=4328` - Próximos partidos Premier League.

## 4. Pasos para Ejecutar
1. Clonar el repositorio.
2. Abrir en Android Studio
3. Sincronizar Gradle.
4. **Importante:** La IP del backend es dinámica debido a (AWS Academy). Verificar `RetrofitInstance.kt` y pegar la nueva IP antes de compilar.
5. Ejecutar en Emulador o Dispositivo Físico.

### 2. Backend 
Si desea correr el backend en AWS:
1. sudo service mysql start
2. cd backend
3. pm2 start server.js --name backend

## 5. Evidencias Visuales (Rúbrica)

### A. Ejecución de Tests (Coverage > 80%)
<img width="896" height="618" alt="image" src="https://github.com/user-attachments/assets/76c9d262-6166-4b38-91fc-c43792b5dd56" />



### B. APK Firmado y KeyStore
<img width="978" height="442" alt="Captura de pantalla 2025-11-24 031421" src="https://github.com/user-attachments/assets/800ee995-d5e9-49f3-84aa-1f0ea1437ff4" />

<img width="649" height="750" alt="Captura de pantalla 2025-11-24 025538" src="https://github.com/user-attachments/assets/1c396f35-ace4-493d-988a-38f66a470604" />




### C. Planificación Trello
<img width="1426" height="754" alt="image" src="https://github.com/user-attachments/assets/687a94fe-daf8-41e1-ba2c-447accd2a10e" />
