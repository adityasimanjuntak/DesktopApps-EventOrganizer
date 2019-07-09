package ae.config;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aditya
 */
public class koneksi {



    private Connection connect;
    private String driverName = "net.sourceforge.jtds.jdbc.Driver"; // Driver Untuk Koneksi Ke SQLServer   
    private String jdbc = "jdbc:jtds:sqlserver://";
    private String host = "192.168.1.24:"; // IP Host PC-Ridho
    private String port = "1433"; // Port Default SQLServer   
    private String database = "AmikomEvent"; // Ini Database yang akan digunakan   
    private String url = jdbc + host + port + database;
    private String username = "sa"; // username default SQLServer   
    private String password = "amikom";

    public Connection getKoneksi() throws SQLException {
        if (connect == null) {
            try {
                Class.forName(driverName);
                System.out.println("Class Driver Ditemukan");
                try {
                    connect = DriverManager.getConnection(url, username, password);
                    System.out.println("Koneksi Database Sukses");
                } catch (SQLException se) {
                    System.out.println("Koneksi Database Gagal : " + se);
                    System.exit(0);
                }
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Class Driver Tidak Ditemukan, Terjadi Kesalahan Pada : " + cnfe);
                System.exit(0);
            }
        }
        return connect;
    }
}
