# Examen MercadoLibre - Mutant Detector

Proyecto desarrollado para la asignatura **Desarrollo de Software**.

*   **Nombre:** Retamales Lazo Juan Lucas
*   **Comisión:** 3K9
*   **Año:** 2025
*   **Institución:** UTN

## Instrucciones de cómo ejecutar el programa o la API

### Prerrequisitos
- **Java 17** o superior instalado.
- **Gradle** (opcional, el proyecto incluye el wrapper).

### Ejecución

#### Opción 1: Desde la terminal (Recomendado)
1. Abre una terminal en la carpeta raíz del proyecto.
2. Ejecuta el siguiente comando:

**En Windows:**
```bash
./gradlew bootRun
```

**En Linux/Mac:**
```bash
./gradlew bootRun
```

#### Opción 2: Desde tu IDE (IntelliJ, Eclipse, VS Code)
1. Abre el proyecto en tu IDE de preferencia.
2. Navega hasta la clase principal:
   `src/main/java/org/example/MutantDetectorApplication.java`
3. Haz clic derecho sobre el archivo o dentro del código y selecciona **"Run 'MutantDetectorApplication'"** (o el botón de Play verde).

#### Opción 3: Generar y ejecutar JAR (Producción)
Si necesitas generar el ejecutable `.jar`:

1. Generar el archivo:
   ```bash
   ./gradlew bootJar
   ```
2. El archivo se generará en la carpeta `build/libs/`.
3. Ejecutar el archivo generado:
   ```bash
   java -jar build/libs/ExamenMercado-1.0-SNAPSHOT.jar
   ```

### Acceso a la API
Una vez que la aplicación inicie (verás un mensaje como `Started MutantDetectorApplication in ...`), puedes acceder a:

- **Swagger UI (Documentación interactiva):**
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

- **Consola H2 (Base de datos en memoria):**
  [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - **JDBC URL:** `jdbc:h2:mem:testdb`
  - **User:** `sa`
  - **Password:** *(dejar vacío)*

- **URL Base de la API:**
  `http://localhost:8080`

- **Página en Render:**
  [https://examenmercadolibre-77tq.onrender.com/swagger-ui/index.html](https://examenmercadolibre-77tq.onrender.com/swagger-ui/index.html)

---
*Este proyecto implementa una API REST para la detección de mutantes basada en secuencias de ADN, desplegada en Render y con base de datos H2.*
