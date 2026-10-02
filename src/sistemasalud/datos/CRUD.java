package sistemasalud.datos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CRUD {
    private final Path ruta;
    
    public CRUD(String archivoNombre) {
        if (archivoNombre == null || archivoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del archivo no puede estar vacío.");
        }
        ruta = resolverRutaDatos().resolve(archivoNombre).toAbsolutePath().normalize();
    }

    private static Path resolverRutaDatos() {
        try {
            Path ubicacion = Path.of(CRUD.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            Path directorioBase = Files.isDirectory(ubicacion)
                    ? Path.of(System.getProperty("user.dir"))
                    : ubicacion.getParent();
            if (directorioBase == null) {
                throw new IllegalStateException(
                        "No se pudo determinar el directorio de la aplicación.");
            }
            return directorioBase;
        } catch (URISyntaxException ex) {
            throw new IllegalStateException(
                    "No se pudo determinar la ubicación de la aplicación.", ex);
        }
    }
    
    protected synchronized ArrayList<String[]> readFullData() throws IOException {
        List<String> data = leerLineas();
        ArrayList<String[]> finalData = new ArrayList<>();
        
        for (String linea : data) {
            if (!linea.isBlank()) {
                finalData.add(linea.split(";", -1));
            }
        }
        
        return finalData;
    }
    
    protected synchronized void updateRow(int index, String content) throws IOException {
        validarContenido(content);
        List<String> lineas = leerLineas();
        validarIndice(index, lineas.size());
        lineas.set(index, content);
        escribirLineas(lineas);
    }
    
    protected synchronized void addRow(String content) throws IOException {
        validarContenido(content);
        List<String> lineas = leerLineas();
        lineas.add(content);
        escribirLineas(lineas);
    }
    
    protected synchronized void removeRow(int index) throws IOException {
        List<String> lineas = leerLineas();
        validarIndice(index, lineas.size());
        lineas.remove(index);
        escribirLineas(lineas);
    }

    private List<String> leerLineas() throws IOException {
        Path directorio = ruta.getParent();
        if (directorio != null) {
            Files.createDirectories(directorio);
        }
        if (Files.notExists(ruta)) {
            try {
                Files.createFile(ruta);
            } catch (java.nio.file.FileAlreadyExistsException ex) {
                // Another application instance created the file first.
            }
        }
        ArrayList<String> lineas = new ArrayList<>(
                Files.readAllLines(ruta, StandardCharsets.UTF_8));
        lineas.removeIf(String::isBlank);
        return lineas;
    }

    private void escribirLineas(List<String> lineas) throws IOException {
        String prefijoTemporal = ruta.getFileName().toString();
        if (prefijoTemporal.length() < 3) {
            prefijoTemporal = (prefijoTemporal + "___").substring(0, 3);
        }
        Path temporal = Files.createTempFile(ruta.getParent(), prefijoTemporal, ".tmp");
        try {
            Files.write(temporal, lineas, StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            try {
                Files.move(temporal, ruta,
                        StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temporal, ruta, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporal);
        }
    }

    private void validarContenido(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("El registro no puede estar vacío.");
        }
        if (content.indexOf('\n') >= 0 || content.indexOf('\r') >= 0) {
            throw new IllegalArgumentException("El registro debe ocupar una sola línea.");
        }
    }

    private void validarIndice(int index, int cantidad) {
        if (index < 0 || index >= cantidad) {
            String rango = cantidad == 0 ? "No hay registros para modificar."
                    : "El ID debe estar entre 0 y " + (cantidad - 1) + ".";
            throw new IndexOutOfBoundsException(rango);
        }
    }
}
