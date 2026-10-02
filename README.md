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
- Persistencia basada en archivos UTF-8 en la carpeta `datos/`

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

Los usuarios se almacenan como `nombre;contraseña`, un registro por línea.
Las altas, modificaciones y eliminaciones reemplazan el archivo de forma
atómica cuando el sistema de archivos lo permite. Los errores de lectura,
registros inválidos e identificadores fuera de rango se muestran en la
interfaz en lugar de tratarse como una lista vacía.

## Datos de los módulos

Personal, pacientes, citas y medicamentos también usan archivos UTF-8 en
`datos/`, con un registro por línea y campos separados por punto y coma:

- `personal.txt`: nombre, especialidad, teléfono y correo.
- `pacientes.txt`: nombre, fecha de nacimiento, teléfono y dirección.
- `citas.txt`: paciente, personal médico, fecha, hora y motivo.
- `medicamentos.txt`: nombre, presentación, existencia e indicaciones.

Desde cada formulario se pueden añadir, consultar, actualizar y eliminar
registros. El ID mostrado en la tabla empieza en 0 y corresponde a la posición
del registro en el archivo. Todos los campos son obligatorios y no admiten
punto y coma ni saltos de línea.

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
