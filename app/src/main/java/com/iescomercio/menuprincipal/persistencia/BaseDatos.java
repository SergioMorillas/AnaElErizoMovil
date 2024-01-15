package com.iescomercio.menuprincipal.persistencia;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {
    private Connection conexion;
    private final String algoritmo;


    /**
     * Constructor completo
     *
     * @param algoritmo El algoritmo a utilizar, puede ser MD5, SHA-1, SHA-256
     */
    public BaseDatos(String algoritmo, String ip, String usuario, String contrasena, String baseDatos) {
        this.algoritmo = algoritmo;

        String connectionUrl = "jdbc:jtds:sqlserver://" + ip + ":1433;"
                + "database=" + baseDatos + ";"
                + "user=" + usuario + ";"
                + "password=" + contrasena + ";"
                + "encrypt=true;"
                + "trustServerCertificate=true;";
        try {
            this.conexion = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            String error = e.getMessage();
            System.out.println(error);
        }
    }

    //TODO metodo para añadir los usuarios a la base de datos
    public boolean anadeUsuario(String nombre, String contrasena) {
        Statement sentencia;
        contrasena = cifraContrasena(contrasena);
        String consulta;
        int p = 0;
        try {
            sentencia = conexion.createStatement();
            consulta = String.format("INSERT INTO [quillquest].[dbo].[usuario](" +
                            "[quillquest].[dbo].[usuario].[nombre]," +
                            "[quillquest].[dbo].[usuario].[hash]) " +
                            "VALUES ('%s', '%s')",
                    nombre, contrasena);
            p = sentencia.executeUpdate(consulta);
            System.out.println(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p!=0;
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

            while (hashtext.length() < 32) hashtext = "0".concat(hashtext);

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que recibe nombre y contraseña en texto plano, comprueba si son correctas y devuelve un digito
     *
     * @param nombre     nombre del usuario
     * @param contrasena contraseña del usuario en texto plano
     * @return devuelve 0 si es correcto, 1 si el nombre no existe y 2 si la contraseña no corresponde
     */
    public int compruebaLogin(String nombre, String contrasena) {
        if (!compruebaNombre(nombre)) return 1;
        if (!compruebaContrasena(nombre, contrasena)) return 2;
        return 0;
    }

    public boolean compruebaNombre(String nombre) {
        try {
            Statement sentencia = conexion.createStatement();
            String consulta = String.format("SELECT TOP (1000) [nombre]\n" +
                    " FROM [quillquest].[dbo].[usuario] where nombre='%s'", nombre);
            ResultSet set = sentencia.executeQuery(consulta);
            while (set.next()) {
                String p = set.getString(1);
                if (p != null) return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    private boolean compruebaContrasena(String nombre, String contrasena) {
        try {
            Statement sentencia = conexion.createStatement();
            String consulta = String.format("SELECT TOP (1000) [hash]\n" +
                    "  FROM [quillquest].[dbo].[usuario] where nombre='%s' and hash='%s'", nombre, contrasena);
            ResultSet set = sentencia.executeQuery(consulta);
            while (set.next()) {
                String p = set.getString(1);
                if (p != null) return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }
    //TODO metodo para mostrar las estadisticas por nombre

    /**
     * @param nombre El nombre del usuario del que buscas las estadisticas
     * @return Los tres datos de las estadisticas por orden, primero muertes,
     * luego resurecciones, y por ultimo victorias
     */
    public int[] muestraEstadisticasNombre(String nombre) {
        int[] valoresDevolver = null;
        try {
            Statement sentencia = conexion.createStatement();
            String consulta = String.format("SELECT TOP (1) \n" +
                    "AVG([quillquest].[dbo].[estadisticas].[vecesMuerto]),\n" +
                    "AVG([quillquest].[dbo].[estadisticas].[vecesResucitado]),\n" +
                    "AVG([quillquest].[dbo].[estadisticas].[partidasGanadas])\n" +
                    "  FROM [quillquest].[dbo].[estadisticas] \n" +
                    "  JOIN [quillquest].[dbo].[usuario] \n" +
                    "  ON [quillquest].[dbo].[usuario].[idUsuario] = [quillquest].[dbo].[estadisticas].[idUsuario]" +
                    "  WHERE [quillquest].[dbo].[usuario].[nombre] = '%s'", nombre);
            ResultSet set = sentencia.executeQuery(consulta);
            while (set.next()) {
                valoresDevolver= new int[3];
                valoresDevolver[0] = set.getInt(1);
                valoresDevolver[1] = set.getInt(2);
                valoresDevolver[2] = set.getInt(3);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valoresDevolver;
    }

    //TODO metodo para mostar las estadisticas generales

    /**
     * @return Los tres datos de las estadisticas por orden, primero muertes,
     * luego resurecciones, y por ultimo victorias
     */
    public int[] muestraEstadisticasGenerales() {
        int[] valoresDevolver = null;
        try {
            Statement sentencia = conexion.createStatement();
            String consulta = "SELECT TOP (1) \n" +
                    "AVG([quillquest].[dbo].[estadisticas].[vecesMuerto]),\n" +
                    "AVG([quillquest].[dbo].[estadisticas].[vecesResucitado]),\n" +
                    "AVG([quillquest].[dbo].[estadisticas].[partidasGanadas])\n" +
                    "  FROM [quillquest].[dbo].[estadisticas] \n" +
                    "  JOIN [quillquest].[dbo].[usuario] \n" +
                    "  ON [quillquest].[dbo].[usuario].[idUsuario] = [quillquest].[dbo].[estadisticas].[idUsuario]";
            ResultSet set = sentencia.executeQuery(consulta);
            while (set.next()) {
                valoresDevolver= new int[3];
                valoresDevolver[0] = set.getInt(1);
                valoresDevolver[1] = set.getInt(2);
                valoresDevolver[2] = set.getInt(3);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valoresDevolver;
    }
}