/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.impl;

import ae.dao.api.IRuanganDao;
import ae.model.Peralatan;
import ae.model.Ruangan;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aditya
 */
public class RuanganDao extends UnicastRemoteObject implements IRuanganDao {

    private Connection conn = null;
    private String strSql = "";

    public RuanganDao(Connection conn) throws RemoteException {
        this.conn = conn;
    }

    @Override
    public String GenerateKDRuangan(Ruangan R) throws RemoteException {
        String result = null;
        String x = "KDR-";
        System.out.println("Method Generate Kode Daftar Ruangan diakses secara remote");
        strSql = "SELECT * FROM Detil_Ruangan ORDER BY KD_DR DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String ruangan_id = rs.getString("KD_DR").substring(4);
                String AN = "" + (Integer.parseInt(ruangan_id) + 1);
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
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String GenerateIDRuangan(Ruangan R) throws RemoteException {
        String result = null;
        String x = "R-";
        System.out.println("Method Generate ID Ruangan diakses secara remote");
        strSql = "SELECT * FROM Ruangan ORDER BY Ruangan_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String ruangan_id = rs.getString("Ruangan_id").substring(2);
                String AN = "" + (Integer.parseInt(ruangan_id) + 1);
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
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String GenerateIDSarpras(Ruangan R) throws RemoteException {
        String result = null;
        String x = "SP-";
        System.out.println("Method Generate ID Sarana Prasarana diakses secara remote");
        strSql = "SELECT * FROM Detil_Ruangan ORDER BY DR_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String ruangan_id = rs.getString("DR_id").substring(3);
                String AN = "" + (Integer.parseInt(ruangan_id) + 1);
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
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int saveDataRuanganInti(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Ruangan diakses secara remote");
        strSql = "INSERT INTO Ruangan (Ruangan_id, Ruangan_nama) VALUES (?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, R.getRuangan_id());
            ps.setString(2, R.getRuangan_nama());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int saveDataDetilRuangan(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Detil Ruangan diakses secara remote");
        strSql = "INSERT INTO Detil_Ruangan (KD_DR, DR_id, DR_tanggal, DR_jam, DR_detil_sapras, DR_Status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, R.getKd_daftar());
            ps.setString(2, R.getRuangan_id());
            ps.setString(3, R.getRuangan_tanggal());
            ps.setString(4, R.getRuangan_jam());
            ps.setString(5, R.getRuangan_sapras_id());
            ps.setString(6, R.getRuangan_status());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int saveDataDetilSapras(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Detil Sarana Prasarana diakses secara remote");
        strSql = "INSERT INTO Detil_Sapras (DetSapras_id, DetSapras_id_alat, DetSapras_jumlah) VALUES (?, ?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, R.getRuangan_sapras_id());
            ps.setString(2, R.getRuangan_id_alat());
            ps.setInt(3, R.getRuangan_jumlah_alat());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Ruangan> getAll() throws RemoteException {
        // buat objek List (class Java Collection)
        List<Ruangan> daftarRuangan = new ArrayList<Ruangan>();
        System.out.println("Method getAll DetilRuangan diakses secara remote");
        strSql = "SELECT KD_DR, DR_id, Dr_tanggal,Dr_jam, DR_detil_sapras, DR_status From Detil_Ruangan WHERE DR_Status ='Tersedia'";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                Ruangan R = new Ruangan();
                R.setKd_daftar(rs.getString("KD_DR"));
                R.setRuangan_id(rs.getString("DR_id"));
                R.setRuangan_tanggal(rs.getString("DR_tanggal"));
                R.setRuangan_jam(rs.getString("Dr_jam"));
                R.setRuangan_sapras_id(rs.getString("DR_detil_Sapras"));
                R.setRuangan_status(rs.getString("DR_Status"));

                // simpan objek barang ke dalam objek class List
                daftarRuangan.add(R);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarRuangan;
    }

    @Override
    public List<Ruangan> getAllSarpras() throws RemoteException {
        // buat objek List (class Java Collection)
        List<Ruangan> daftarRuangan = new ArrayList<Ruangan>();
        System.out.println("Method getAll Detil Sapras diakses secara remote");
        strSql = "SELECT Detsapras_id, Detsapras_id_alat, Detsapras_jumlah From Detil_Sapras ORDER BY Detsapras_id, Detsapras_id_alat ASC";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                Ruangan R = new Ruangan();
                R.setRuangan_sapras_id(rs.getString("Detsapras_id"));
                R.setRuangan_id_alat(rs.getString("Detsapras_id_alat"));
                R.setRuangan_jumlah_alat(rs.getInt("Detsapras_jumlah"));

                // simpan objek barang ke dalam objek class List
                daftarRuangan.add(R);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarRuangan;
    }
    
    @Override
    public List<Ruangan> getAllRuangan() throws RemoteException {
        // buat objek List (class Java Collection)
        List<Ruangan> daftarRuangan = new ArrayList<Ruangan>();
        System.out.println("Method getAll Ruangan diakses secara remote");
        strSql = "SELECT Ruangan_id, Ruangan_nama From Ruangan";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                Ruangan R = new Ruangan();
                R.setRuangan_id(rs.getString("Ruangan_id"));
                R.setRuangan_nama(rs.getString("Ruangan_nama"));
                // simpan objek barang ke dalam objek class List
                daftarRuangan.add(R);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarRuangan;
    }
    @Override
    public int updateRuangan(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method update Ruangan diakses secara remote");
        strSql = "UPDATE Ruangan SET Ruangan_nama = ?"
                + "WHERE Ruangan_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, R.getRuangan_nama());
            ps.setString(2, R.getRuangan_id());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int updateDetilRuangan(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method update Ruangan diakses secara remote");
        strSql = "UPDATE Detil_Ruangan SET DR_ID = ?, DR_tanggal = ?, Dr_jam = ?, DR_detil_sapras = ?, DR_Status = ?"
                + "WHERE KD_DR = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, R.getRuangan_id());
            ps.setString(2, R.getRuangan_tanggal());
            ps.setString(3, R.getRuangan_jam());
            ps.setString(4, R.getRuangan_sapras_id());
            ps.setString(5, R.getRuangan_status());
            ps.setString(6, R.getKd_daftar());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int updateDetilSapras(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method update Detil Sapras diakses secara remote");
        strSql = "UPDATE Detil_Sapras SET DetSapras_jumlah = ?"
                + "WHERE DetSapras_id = ? AND DetSapras_id_alat = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setInt(1, R.getRuangan_jumlah_alat());
            ps.setString(2, R.getRuangan_sapras_id());
            ps.setString(3, R.getRuangan_id_alat());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Ruangan> getByKDSarpras(String kd_sarpras) throws RemoteException {
        // buat objek List (class Java Collection)
        List<Ruangan> daftarRuangan = new ArrayList<Ruangan>();
        System.out.println("Method getByKDSarpras diakses secara remote");
        strSql = "Select Detsapras_id, Detsapras_id_alat, Detsapras_jumlah From Detil_Sapras WHERE Detsapras_id LIKE ? ORDER BY Detsapras_id, Detsapras_id_alat ASC";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, "%" + kd_sarpras + "%");
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                Ruangan R = new Ruangan();
                R.setRuangan_sapras_id(rs.getString("Detsapras_id"));
                R.setRuangan_id_alat(rs.getString("Detsapras_id_alat"));
                R.setRuangan_jumlah_alat(rs.getInt("Detsapras_jumlah"));
                // simpan objek barang ke dalam objek class List
                daftarRuangan.add(R);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarRuangan;
    }

    @Override
    public int deleteSapras(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method delete Sarpras diakses secara remote");
        strSql = "DELETE FROM Detil_Sapras "
                + "WHERE DetSapras_id = ? AND DetSapras_id_alat = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, R.getRuangan_sapras_id());
            ps.setString(2, R.getRuangan_id_alat());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deleteDetilRuangan(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method delete DetilRuangan diakses secara remote");
        strSql = "DELETE FROM Detil_Ruangan "
                + "WHERE KD_DR = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, R.getKd_daftar());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deleteRuangan(Ruangan R) throws RemoteException {
        int result = 0;
        System.out.println("Method delete Ruangan diakses secara remote");
        strSql = "DELETE FROM Ruangan WHERE Ruangan_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, R.getRuangan_id());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RuanganDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
