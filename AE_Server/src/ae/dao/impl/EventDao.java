/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.impl;

import ae.dao.api.IEventDao;
import ae.model.Event;
import ae.model.Ruangan;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aditya
 */
public class EventDao extends UnicastRemoteObject implements IEventDao {

    private Connection conn = null;
    private String strSql = "";

    public EventDao(Connection conn) throws RemoteException {
        this.conn = conn;
    }

    @Override
    public String GenerateKDEvent(Event E) throws RemoteException {
        String result = null;
        String x = "E-";
        System.out.println("Method Generate Kode Event diakses secara remote");
        strSql = "SELECT * FROM Detil_Event ORDER BY Event_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String ruangan_id = rs.getString("Event_id").substring(2);
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
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String GenerateKDTambahAlat(Event E) throws RemoteException {
        String result = null;
        String x = "TA-";
        System.out.println("Method Generate Kode Tambah Peralatan diakses secara remote");
        strSql = "SELECT * FROM Detil_Event ORDER BY Event_eks_alat DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String ruangan_id = rs.getString("Event_eks_alat").substring(3);
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
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int saveDataEvent(Event E) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Event diakses secara remote");
        strSql = "INSERT INTO Detil_Event (Event_id, Event_nama, Event_tanggal, EO_ID, Event_Category, Event_Ruangan, Event_eks_alat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, E.getEvent_id());
            ps.setString(2, E.getEvent_nama());
            ps.setString(3, E.getEvent_tanggal());
            ps.setString(4, E.getEO_id());
            ps.setString(5, E.getEvent_kategori());
            ps.setString(6, E.getEvent_ruangan());
            ps.setString(7, E.getTambah_alat());

            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int saveDataDetilTambahAlat(Event E) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Detil Tambah Alat diakses secara remote");
        strSql = "INSERT INTO Detil_Tambahan_Peralatan (DTP_ID, DTP_id_alat, Kuantitas) VALUES (?, ?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, E.getTambah_alat());
            ps.setString(2, E.getId_alat());
            ps.setInt(3, E.getKuantitas());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int updateRuangan(Event E) throws RemoteException {
        int result = 0;
        System.out.println("Method update Ruangan diakses secara remote");
        strSql = "UPDATE Detil_Ruangan SET DR_status = 'Tidak Tersedia (Booked)'"
                + "WHERE KD_DR = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, E.getEvent_ruangan());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
