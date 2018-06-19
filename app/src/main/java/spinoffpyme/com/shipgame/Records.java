package spinoffpyme.com.shipgame;

/**
 * Created by adrian.montes on 10/3/18.
 */

//Esta clase se usa para guardar los records personales
public class Records {

private String Usuario;


    public Records() {
    }


    public Records(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
