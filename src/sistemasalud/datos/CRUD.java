package sistemasalud.datos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CRUD {
    private final Path ruta;
    
    public CRUD(String archivoNombre) {
        ruta = Path.of(archivoNombre);
    }
    
    protected ArrayList<String[]> readFullData() throws IOException {
        List<String> data = Files.readAllLines(ruta);
        ArrayList<String[]> finalData = new ArrayList<>();
        
        for (String obj_data : data) {
            String[] properties = obj_data.split(";");
            finalData.add(properties);
        }
        
        return finalData;
    }
    
    protected void updateRow(int index, String content) throws IOException {
        List<String> lineas = Files.readAllLines(ruta);
        ArrayList<String> lineasMod = new ArrayList<>();
        
        for (int i = 0; i < lineas.size(); i++) {
            lineasMod.add(i == index ? content : lineas.get(i));
        }
        
        Files.write(ruta, lineasMod);
    }
    
    protected void addRow(String content) throws IOException {
        Files.writeString(ruta, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    
    protected void removeRow(int index) throws IOException {
        List<String> lineas = Files.readAllLines(ruta);
        ArrayList<String> lineasMod = new ArrayList<>();
        
        for (int i = 0; i < lineas.size(); i++) {
            if (i != index) {
                lineasMod.add(lineas.get(i));
            }
        }
        
        Files.write(ruta, lineasMod);
    }
}
