/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.impl;

import ae.dao.api.IPeralatanDao;
import ae.model.Kategori;
import ae.model.Peralatan;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aditya
 */
public class PeralatanDao extends UnicastRemoteObject implements IPeralatanDao {

    private Connection conn = null;
    private String strSql = "";

    public PeralatanDao(Connection conn) throws RemoteException {
        this.conn = conn;
    }

    @Override
    public int saveDataPeralatan(Peralatan P) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Peralatan diakses secara remote");
        strSql = "INSERT INTO Peralatan (Peralatan_id, Peralatan_nama, Peralatan_jumlah) VALUES (?, ?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, P.getPeralatan_id());
            ps.setString(2, P.getPeralatan_nama());
            ps.setInt(3, P.getPeralatan_jumlah());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PeralatanDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String GenerateIDPeralatan(Peralatan P) throws RemoteException {
        String result = null;
        String x = "P";
        System.out.println("Method Generate diakses secara remote");
        strSql = "SELECT * FROM Peralatan ORDER BY Peralatan_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String kat_id = rs.getString("Peralatan_id").substring(1);
                String AN = "" + (Integer.parseInt(kat_id) + 1);
                String nol = "";
                switch (AN.length()) {
                    case 1:
                        nol = "00";
                        break;
                    case 2:
                        nol = "0";
                        break;
                    case 3:
                        nol = "";
                        break;
                    default:
                        break;
                }
                result = x + nol + AN;
            } else {
                result = x + "00" + "1";
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PeralatanDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Peralatan> getAll() throws RemoteException {
        // buat objek List (class Java Collection)
        List<Peralatan> daftarPeralatan = new ArrayList<Peralatan>();
        System.out.println("Method getAll Peralatan diakses secara remote");
        strSql = "SELECT Peralatan_id, Peralatan_nama, Peralatan_jumlah From Peralatan";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                Peralatan P = new Peralatan();
                P.setPeralatan_id(rs.getString("Peralatan_id"));
                P.setPeralatan_nama(rs.getString("Peralatan_nama"));
                P.setPeralatan_jumlah(rs.getInt("Peralatan_jumlah"));
                // simpan objek barang ke dalam objek class List
                daftarPeralatan.add(P);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeralatanDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarPeralatan;
    }

    @Override
    public int updatePeralatan(Peralatan P) throws RemoteException {
        int result = 0;
        System.out.println("Method update Peralatan diakses secara remote");
        strSql = "UPDATE Peralatan SET Peralatan_nama = ?, Peralatan_jumlah = ?"
                + "WHERE Peralatan_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, P.getPeralatan_nama());
            ps.setInt(2, P.getPeralatan_jumlah());
            ps.setString(3, P.getPeralatan_id());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PeralatanDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deletePeralatan(Peralatan P) throws RemoteException {
        int result = 0;
        System.out.println("Method delete Peralatan diakses secara remote");
        strSql = "DELETE FROM Peralatan "
                + "WHERE Peralatan_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, P.getPeralatan_id());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PeralatanDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
