/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.main.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ae.dao.impl.*;

/**
 *
 * @author Aditya
 */
public class ServerMain {

    public static void main(String[] args) {
        try {
            // mendaftarkan JDBC Driver
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            try {
                // Akses ke Komputer - Laptop Ridho IP : 192.168.1.24 Port 1433
                String url = "jdbc:jtds:sqlserver://localhost:1433/AmikomEvent";
                String user = "sa";
                String password = "amikom";
                // buat objek Connection
                Connection conn = DriverManager.getConnection(url, user, password);
                try {
                    // buat objek BarangDao dan SupplierDao
                    EventOrganizerDao eoDao = new EventOrganizerDao(conn);
                    LoginDao loginDao = new LoginDao(conn);
                    ErrorReportDao errorDao = new ErrorReportDao(conn);
                    UserDao userDao = new UserDao(conn);
                    KategoriDao kategoriDao = new KategoriDao(conn);
                    PeralatanDao peralatanDao = new PeralatanDao(conn);
                    RuanganDao ruanganDao = new RuanganDao(conn);
                    EventDao eventDao = new EventDao(conn);
                    // buat objek RMI registry menggunakan port default RMI (1099)
                    Registry registry = LocateRegistry.createRegistry(1099);
                    // mendaftarkan objek barangDao dan supplierDao ke RMI Registry
                    registry.rebind("eoDao", eoDao);
                    registry.rebind("loginDao", loginDao);
                    registry.rebind("errorDao", errorDao);
                    registry.rebind("userDao", userDao);
                    registry.rebind("kategoriDao", kategoriDao);
                    registry.rebind("peralatanDao", peralatanDao);
                    registry.rebind("ruanganDao", ruanganDao);
                    registry.rebind("eventDao",eventDao);
                    System.out.println(">>> Server Is Running <<<\n");
                } catch (RemoteException ex) {
                    System.out.println(">>> Server Failed To Run <<<\n");
                    Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
