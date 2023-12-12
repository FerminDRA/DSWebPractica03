package org.uv.jsf;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "registroBean")
@SessionScoped
public class RegistroBean implements Serializable {
    private String nombre;
    private String contrasena;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String registrar() {
        PreparedStatement pstmt = null;
        
        try {
            Conexion cx = Conexion.getConexion();
            
            String username = nombre; // Usar directamente la variable miembro
            String password = contrasena; // Usar directamente la variable miembro
            String sql = "INSERT INTO usuarios (nombre, pass) VALUES (?, ?)";
            pstmt = cx.con.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", "Usuario registrado correctamente."));
            
            return "login.xhtml"; // Redirecciona a la página de inicio después del registro exitoso.
        } catch (SQLException ex) {
            Logger.getLogger(RegistroBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al registrar el usuario."));
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return null; // Permanece en la misma página en caso de error.
    }

    public String regresar() {
        return "login.xhtml"; // Redirecciona a la página de inicio.
    }
}
