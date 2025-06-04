📦 INFOTEL – Plataforma Inteligente de Recomendación de Prendas
Proyecto desarrollado para la hackatón de la Universidad Peruana Unión, con el objetivo de modernizar la experiencia de compra de ropa mediante inteligencia artificial, geolocalización y automatización.

📁 Estructura del Repositorio

📦 /Wrapkit-Angular-Blog-free     → Frontend Angular (módulos de recomendación e IA)
📦 /hackaton                      → Backend Java Spring Boot
📄  infotell.sql                  → Script SQL con base de datos (usuarios + productos)
🛠️ Requisitos Previos
Node.js y Angular CLI instalados

Java 17+ y Maven

MySQL Server

Postman (opcional, para pruebas de backend)

🚀 Instrucciones de Uso
1. Clona el repositorio
git clone https://github.com/tu-usuario/infotel-proyecto.git
2. Configura la base de datos
Crea una base de datos llamada infotel en MySQL.

Importa el archivo infotel.sql para crear las tablas y registros de prueba.

3. Ejecuta el backend (Spring Boot)
cd hackaton
./mvnw spring-boot:run
Esto levanta el servidor en:
📍 http://localhost:8080

4. Ejecuta el frontend (Angular)
cd Wrapkit-Angular-Blog-free
npm install
ng serve
Esto levanta la app en:
🌐 http://localhost:4200

👤 Usuarios de Prueba
Los siguientes usuarios están registrados en la base de datos:

ID	Nombre	Correo
1	Juana	juana@gmail.com
2	David	david@gmail.com
3	Gloria	gloria@gmail.com
4	Pepito	pepito@gmail.com

🔐 Iniciar Sesión
Haz clic en Iniciar sesión (parte superior derecha).

Ingresa uno de los correos registrados (ej: david@gmail.com).

Al iniciar sesión se habilitan:

Asistente de recomendación semanal.

Asistente IA con texto personalizado.

Consulta automática del clima actual.

Productos recomendados según género y temperatura.

📍 Ubicación y Clima
Al iniciar sesión, la web pedirá acceso a tu ubicación.

Si aceptas, se usará tu latitud y longitud para consultar el clima actual vía Open-Meteo.

Si rechazas la ubicación, los módulos de recomendación no se mostrarán y se te pedirá reintentar.

🤖 Funcionalidad Inteligente
Recomendador por Clima
Basado en la temperatura, se clasifica como:

clima = frio (menos de 18°C)

clima = calor (18°C o más)

El backend filtra productos según clima y género.

Asistente de IA (Groq + LLaMA)
Genera una descripción amigable y personalizada usando el modelo llama3-70b-8192 de Groq.

Toma como contexto:

Nombre del usuario

Temperatura y clima actual

Un producto real de la base de datos

Devuelve una explicación de por qué ese producto es ideal.

🧪 Pruebas de Ubicación (Chrome DevTools)
Puedes simular diferentes ubicaciones desde:

DevTools > More Tools > Sensors > Location

Ejemplos:

Juliaca: -15.5, -70.1 (clima frío)

Desierto del Sahara: 25.0, 13.0 (clima caluroso)

✅ Prueba final para jurado
Iniciar sesión con david@gmail.com

Permitir ubicación

Ver las tarjetas de productos recomendados

Leer la sugerencia personalizada del Asistente IA

Cambiar ubicación desde DevTools y observar el cambio dinámico en los productos
