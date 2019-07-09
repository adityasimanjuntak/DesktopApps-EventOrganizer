/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.impl;

import ae.dao.api.IKategoriDao;
import ae.model.EventOrganizer;
import ae.model.Kategori;
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
public class KategoriDao extends UnicastRemoteObject implements IKategoriDao {

    private Connection conn = null;
    private String strSql = "";

    public KategoriDao(Connection conn) throws RemoteException {
        this.conn = conn;
    }

    @Override
    public int saveDataKategori(Kategori K) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Kategori diakses secara remote");
        strSql = "INSERT INTO Category (Category_id, Category_name) VALUES (?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, K.getId_kat());
            ps.setString(2, K.getNama_kat());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KategoriDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String GenerateIDKategori(Kategori K) throws RemoteException {
        String result = null;
        String x = "K";
        System.out.println("Method Generate diakses secara remote");
        strSql = "SELECT * FROM Category ORDER BY category_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String kat_id = rs.getString("category_id").substring(1);
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
            Logger.getLogger(KategoriDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Kategori> getAll() throws RemoteException {
        // buat objek List (class Java Collection)
        List<Kategori> daftarKategori = new ArrayList<Kategori>();
        System.out.println("Method getAll Kategori diakses secara remote");
        strSql = "SELECT Category_id, Category_name From Category";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                Kategori K = new Kategori();
                K.setId_kat(rs.getString("Category_id"));
                K.setNama_kat(rs.getString("Category_name"));
                // simpan objek barang ke dalam objek class List
                daftarKategori.add(K);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KategoriDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarKategori;
    }

    @Override
    public int updatekategori(Kategori K) throws RemoteException {
        int result = 0;
        System.out.println("Method update kategori diakses secara remote");
        strSql = "UPDATE Category SET category_name = ?"
                + "WHERE category_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, K.getNama_kat());
            ps.setString(2, K.getId_kat());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KategoriDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deletekategori(Kategori K) throws RemoteException {
        int result = 0;
        System.out.println("Method delete kategori diakses secara remote");
        strSql = "DELETE FROM category "
                + "WHERE category_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, K.getId_kat());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KategoriDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
