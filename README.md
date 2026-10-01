# Sistema de Gestión del Centro de Salud Ganimedes

Aplicación de escritorio desarrollada en Java Swing para gestionar la administración de un centro de salud. El sistema permite controlar accesos, usuarios, personal médico, pacientes, citas y módulos de gestión con una interfaz moderna y funcional.

## Descripción general

Este proyecto está pensado para una clínica o centro de salud que necesita una herramienta sencilla para manejar:

- Inicio de sesión y control de accesos
- Gestión de usuarios del sistema
- Registro y administración de personal médico
- Gestión de pacientes
- Control de citas
- Navegación por módulos desde un menú principal

## Tecnologías utilizadas

- Java SE
- Swing (GUI)
- NetBeans IDE
- Persistencia basada en archivos de texto en la carpeta `datos/`

## Estructura del proyecto

```text
SistemaSalud/
├── src/                 # Código fuente Java
├── datos/               # Archivos de datos del sistema
├── capturas/            # Capturas de pantalla del proyecto
├── dist/                # Artefactos compilados y JAR
├── build.xml            # Script de compilación de Ant
├── manifest.mf          # Manifest del proyecto
├── README.md            # Documentación del proyecto
└── .gitignore
```

## Requisitos

- Java JDK 8 o superior
- NetBeans IDE (opcional, recomendado para abrir el proyecto)
- Sistema operativo compatible con Java

## Ejecución

### Opción 1: Ejecutar el JAR compilado

Desde la raíz del proyecto:

```bash
java -jar dist/SistemaSalud.jar
```

### Opción 2: Ejecutar desde NetBeans

1. Abre el proyecto en NetBeans.
2. Haz clic derecho sobre el proyecto.
3. Selecciona `Run` o `Ejecutar`.
4. La clase principal es `sistemasalud.SistemaSalud`.

## Credenciales por defecto

El sistema incluye usuarios predefinidos en `datos/usuarios.txt`.

Ejemplo:

```text
ADMIN;ADMIN
```

Puede iniciar sesión con:

- Usuario: `ADMIN`
- Contraseña: `ADMIN`

## Módulos principales

- Personal médico: gestión del personal del centro
- Pacientes: administración de pacientes
- Citas: control de citas médicas
- Usuarios: alta, edición, eliminación y consulta de usuarios

## Capturas del sistema

### Inicio de sesión

![Pantalla de inicio de sesión](./capturas/Captura1.png)

### Menú principal y administración de usuarios

![Menú principal del sistema](./capturas/Captura2.png)
