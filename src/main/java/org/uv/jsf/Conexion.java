/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.jsf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author fermin
 */
public class Conexion {
    private static Conexion conexion=null;
    public Connection con=null;
    
    public static Conexion getConexion() throws SQLException{
        if(conexion==null)
            conexion=new Conexion();
        
        return conexion;
    }
    
    private Conexion() throws SQLException {
        try {
            Context initContext =new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/CrudAlumnos");
            con = dataSource.getConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
