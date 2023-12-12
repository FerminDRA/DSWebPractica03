package org.uv.jsf;

import java.io.Serializable;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "beanIndex")
@SessionScoped
public class BeanIndex implements Serializable {
    private String clave;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Persona> personas; // Una lista para almacenar las personas

    public BeanIndex() {
        personas = new ArrayList<>();
        cargarPersonas();
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public List<Persona> getPersonas() {
        return personas;
    }

    public void cargarPersonas() {
        try {
            Conexion cx = Conexion.getConexion();
            String sql = "SELECT * FROM personas";
            PreparedStatement pstmt = cx.con.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            personas.clear();

            while (resultSet.next()) {
                Persona persona = new Persona();
                persona.setClave(resultSet.getInt("id"));
                persona.setNombre(resultSet.getString("nombre"));
                persona.setDireccion(resultSet.getString("direccion"));
                persona.setTelefono(resultSet.getString("telefono"));
                personas.add(persona);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearPersona() {
        try {
            Conexion cx = Conexion.getConexion();
            String sql = "INSERT INTO personas (id, nombre, direccion, telefono) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = cx.con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(clave)); // Convertir clave a entero
            pstmt.setString(2, nombre);
            pstmt.setString(3, direccion);
            pstmt.setString(4, telefono);
            pstmt.executeUpdate();
            addMessage(FacesMessage.SEVERITY_INFO, "Atención", "Se guardó");
            cargarPersonas();
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarPersona(Persona persona) {
        try {
            Conexion cx = Conexion.getConexion();
            String sql = "UPDATE persona SET nombre = ?, direccion = ?, telefeno = ? WHERE clave = ?";
            PreparedStatement pstmt = cx.con.prepareStatement(sql);
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getDireccion());
            pstmt.setString(3, persona.getTelefono());
            pstmt.setInt(4, persona.getClave());
            pstmt.executeUpdate();

            addMessage(FacesMessage.SEVERITY_INFO, "Atención...", "Se actualizó");
            cargarPersonas();
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarPersona(Persona persona) {
        try {
            Conexion cx = Conexion.getConexion();
            String sql = "DELETE FROM personas WHERE id = ?";
            PreparedStatement pstmt = cx.con.prepareStatement(sql);
            pstmt.setInt(1, persona.getClave());
            pstmt.executeUpdate();

            addMessage(FacesMessage.SEVERITY_INFO, "Atención...", "Se eliminó");
            cargarPersonas();
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        clave = "";
        nombre = "";
        direccion = "";
        telefono = "";
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
}



