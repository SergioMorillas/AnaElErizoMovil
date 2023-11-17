package com.iescomercio.menuprincipal.persistencia;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {
    private String algoritmo = "MD5";

    /**
     * Constructor sin algoritmo, se utilizará por defecto MD5
     */
    public BaseDatos() {
    }

    /**
     * Constructor con algoritmo, se utilizará el especificado
     *
     * @param algoritmo El algoritmo a utilizar, puede ser MD5, SHA-1, SHA-256
     */
    public BaseDatos(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public Connection conexion(String iURL, String iUsuario, String iContrasena) {
        String url = iURL;
        String usuario = iUsuario;
        String contraseña = iContrasena;
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
        }
        return conexion;
    }

    public Connection conexion() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=quillquest";
        String usuario = "sa";
        String contraseña = "P@ssw0rd";
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
        }
        return conexion;
    }

    //TODO metodo para añadir los usuarios a la base de datos
    public boolean anadeUsuario(String nombre, String contraseña, Connection conexion) {

        return true;
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
    //TODO metodo para comparar las contraseñas con el hash

    /**
     * Metodo que recibe nombre y contraseña en texto plano, comprueba si son correctas y devuelve un digito
     * @param nombre nombre del usuario
     * @param contrasena contraseña del usuario en texto plano
     * @param conexion Connection
     * @return devuelve 0 si es correcto, 1 si el nombre no existe y 2 si la contraseña no corresponde
     */
    public int compruebaLogin(String nombre, String contrasena, Connection conexion) {
        if (!compruebaNombre(nombre, conexion)) return 1;
        if (!compruebaContrasena(nombre, cifraContrasena(contrasena), conexion)) return 2;
        return 0;
    }
    //TODO metodo para comparar las contraseñas con el hash
    public boolean compruebaNombre(String nombre, Connection conexion) {

        return false;
    }
    //TODO metodo para comparar las contraseñas con el hash
    public boolean compruebaContrasena(String nombre, String contrasena, Connection conexion) {

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
