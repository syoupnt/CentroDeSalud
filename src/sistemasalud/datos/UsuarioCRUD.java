package sistemasalud.datos;

import java.io.IOException;
import java.util.ArrayList;
import sistemasalud.negocio.model.Usuario;

public class UsuarioCRUD extends CRUD {
    private static UsuarioCRUD instance = null;
    
    private UsuarioCRUD() {
        super("datos/usuarios.txt");
    }
    
    public static UsuarioCRUD getInstance() {
        if (instance == null) {
            instance = new UsuarioCRUD();
        }
        
        return instance;
    }
    
    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        try {
            ArrayList<String[]> fullData = readFullData();
            
            fullData.stream().forEach((data) -> {
                if (data.length == 2) {
                    usuarios.add(
                            new Usuario(
                                    data[0],
                                    data[1]
                            )
                    );
                }
            });
        } catch (IOException ex) {
            System.getLogger(UsuarioCRUD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return usuarios;
    }
    
    public void addUsuario(Usuario user) throws IOException {
        addRow(user.getNombre() + ";" + user.getContrasena());
    }
    
    public void removeUsuario(int index) throws IOException {
        removeRow(index);
    }
    
    public void updateUsuario(int index, Usuario user) throws IOException {
        updateRow(index, user.getNombre() + ";" + user.getContrasena());
    }
}
