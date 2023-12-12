import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.uv.jsf.Conexion;

@ManagedBean
@SessionScoped
public class loginBean implements Serializable {
    private String username;
    private String contrasena;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String iniciarSesion() throws SQLException {
        PreparedStatement pstmt = null;
        Conexion cx = Conexion.getConexion();
            
        String user = this.username; // Usar directamente la variable miembro
        String password = this.contrasena; // Usar directamente la variable miembro
        String sql = "select * from usuarios where nombre=? and pass=?";
        pstmt = cx.con.prepareStatement(sql);
        pstmt.setString(1, user);
        pstmt.setString(2, password);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
                return "index.xhtml";
        } else {
            // Las credenciales son incorrectas, muestra un mensaje de error.
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos."));
            return null; 
        }
    }

    public String irARegistro() {
        // Lógica para redireccionar al usuario a la página de registro.
        return "registro.xhtml"; // Debes retornar la página a la que deseas redireccionar.
    }
}
