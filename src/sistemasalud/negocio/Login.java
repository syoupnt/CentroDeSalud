package sistemasalud.negocio;

import sistemasalud.negocio.model.Usuario;

public class Login {
    private static Login instance = null;
    
    public Usuario usuario;
    
    private Login() {}
    
    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        
        return instance;
    }
    
    public void iniciarSesion(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
}
