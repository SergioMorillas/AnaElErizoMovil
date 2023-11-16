package com.iescomercio.menuprincipal.persistencia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {
    public Connection conexion(String iurl, String iusuario, String icontrasena) {
        String url = iurl;
        String usuario = iusuario;
        String contraseña = icontrasena;
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {}
        return conexion;
    }
    public Connection conexion() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=quillquest";
        String usuario = "sa";
        String contraseña = "P@ssw0rd";
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {}
        return conexion;
    }

}
