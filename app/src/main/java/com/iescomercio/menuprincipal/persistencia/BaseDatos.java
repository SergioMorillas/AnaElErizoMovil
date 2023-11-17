package com.iescomercio.menuprincipal.persistencia;

import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDatos {
    private Connection conexion;
    private String algoritmo = "MD5";
    private String anadeUsuario = "insert into usuario (nombre, hash) values ('?', '?')";
    /**
     * Constructor sin algoritmo, se utilizará por defecto MD5
     */
    public BaseDatos() {
        this("MD5");

    }

    /**
     * Constructor con algoritmo, se utilizará el especificado
     *
     * @param algoritmo El algoritmo a utilizar, puede ser MD5, SHA-1, SHA-256
     */
    public BaseDatos(String algoritmo) {
        this.algoritmo = algoritmo;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=quillquest";
        String usuario = "sa";
        String contraseña = "P@ssw0rd";
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
        }
    }

    public Connection setConexion(String iURL, String iUsuario, String iContrasena) {
        String url = iURL;
        String usuario = iUsuario;
        String contraseña = iContrasena;
        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
        }
        return conexion;
    }

    //TODO metodo para añadir los usuarios a la base de datos
    public boolean anadeUsuario(String nombre, String contrasena) {
        int r=0;
        contrasena = cifraContrasena(contrasena);
        try {
            PreparedStatement sentencia = conexion.prepareCall(anadeUsuario);
            sentencia.setString(1, nombre);
            sentencia.setString(2, contrasena);
            r = sentencia.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    return (r==0)?false:true;
    }

    /**
     * Metodo que cifra las contraseñas en el movil antes de mandarselas a la base de datos
     *
     * @param contrasena La contraseña en texto plano
     * @return La contraseña cifrada
     */
    public String cifraContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            byte[] messageDigest = md.digest(contrasena.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) hashtext = "0" + hashtext;

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO metodo para comprobar si el login ha sido correcto
    /**
     * Metodo que recibe nombre y contraseña en texto plano, comprueba si son correctas y devuelve un digito
     * @param nombre nombre del usuario
     * @param contrasena contraseña del usuario en texto plano
     * @return devuelve 0 si es correcto, 1 si el nombre no existe y 2 si la contraseña no corresponde
     */
    public int compruebaLogin(String nombre, String contrasena) {
        if (!compruebaNombre(nombre)) return 1;
        if (!compruebaContrasena(nombre, cifraContrasena(contrasena))) return 2;
        return 0;
    }
    //TODO metodo para comprobar si existe el nombre en la base de datos
    public boolean compruebaNombre(String nombre) {

        return false;
    }
    //TODO metodo para comparar las contraseñas con el hash
    public boolean compruebaContrasena(String nombre, String contrasena) {

        return false;
    }
    //TODO metodo para mostrar las estadisticas por nombre

    /**
     * @param nombre El nombre del usuario del que buscas las estadisticas
     * @return Los tres datos de las estadisticas por orden, primero muertes,
     * luego resurecciones, y por ultimo victorias
     */
    public int[] muestraEstadisticasNombre(String nombre) {
        return null;
    }

    //TODO metodo para mostar las estadisticas generales
    /**
     * @return Los tres datos de las estadisticas por orden, primero muertes,
     * luego resurecciones, y por ultimo victorias
     */
    public int[] muestraEstadisticasGenerales() {
        return null;
    }
}
