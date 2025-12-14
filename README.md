# ✅ Práctica — Java 17 → Java 21 (Parte 2)

**Concurrencia moderna con Virtual Threads · Scoped Values · Record Patterns · JVM moderna**

> Objetivo: migrar un servicio “clásico” (thread pools + bloqueos) a un servicio moderno basado en **virtual threads**, aplicar **concurrencia estructurada**, sustituir **ThreadLocal por Scoped Values**, y modernizar el dominio usando **records + pattern matching/record patterns**.

---

## 🎯 Qué vas a aprender (en esta práctica)

* **Virtual Threads (Project Loom)**: un hilo por tarea sin saturar pools.
* **Structured Concurrency**: tareas que “viven y mueren juntas”.
* **Scoped Values**: contexto seguro (sustituto moderno de ThreadLocal).
* **Record Patterns + Pattern Matching**: lógica más declarativa y mantenible.
* (Extra) **Notas de JVM moderna**: visión de GC y rendimiento para servicios reales.

---

## ✅ Requisitos

* GitHub account
* Abrir en **GitHub Codespaces** (recomendado) o local con:

  * **JDK 21**
  * **Maven 3.9+** (o Gradle si el profesor lo indica)

---

## 🚀 Cómo ejecutar

### Opción A — Codespaces (recomendado)

1. Click en **Code → Codespaces → Create codespace on main**
2. Espera a que termine la configuración del contenedor
3. En la terminal:

   ```bash
   mvn -q clean test
   mvn -q exec:java
   ```

### Opción B — Local

```bash
mvn -q clean test
mvn -q exec:java
```

---

## 🧩 Estructura del proyecto

```
.
├── .devcontainer/              # Entorno reproducible para Codespaces
├── .vscode/                    # Extensiones y settings recomendados
├── src/
│   ├── main/java/com/cursojava/parte2/
│   │   ├── Main.java
│   │   ├── legacy/             # Versión “antes” (pool + ThreadLocal)
│   │   ├── modern/             # Versión “después” (VT + Scoped Values + patterns)
│   │   └── domain/             # Records / sealed / patterns
│   └── test/java/com/cursojava/parte2/
│       └── ...
├── pom.xml
└── README.md
```

---

## 🧪 Entregable del alumno

1. Crear rama:

   ```bash
   git checkout -b alumno/TU_NOMBRE_APELLIDOS
   git push -u origin alumno/TU_NOMBRE_APELLIDOS
   ```
2. Completar TODOs en `src/main/java/...`
3. Pasar tests:

   ```bash
   mvn -q test
   ```
4. Subir cambios y abrir PR hacia `main`.

---

## 🧭 Pistas de la práctica (qué se evalúa)

* Migración correcta a **Executors.newVirtualThreadPerTaskExecutor()**
* Uso correcto de **StructuredTaskScope** (cancelación y manejo de fallos)
* Sustitución real de **ThreadLocal → Scoped Values**
* Uso claro de **records + pattern matching/record patterns**
* Código legible, sin “magia”, con commits pequeños y mensajes decentes

---

## 🆘 Problemas típicos

* “No reconoce Java 21”: revisa el `devcontainer.json` (Codespaces) o tu JAVA_HOME (local).
* “No pasa tests”: ejecuta `mvn -q -Dtest=NombreTest test` y revisa el fallo exacto.
* “Se cuelga algo”: recuerda que el objetivo es I/O concurrente con VT, no CPU-bound infinito.

---

## 📌 Nota del profesor

Esta práctica representa una migración real de un servicio clásico a un modelo moderno, aplicando:

* Virtual Threads + Structured Concurrency
* Scoped Values para contexto seguro
* Modelado moderno con records y patterns

¡Haz commits pequeños y razonados!
