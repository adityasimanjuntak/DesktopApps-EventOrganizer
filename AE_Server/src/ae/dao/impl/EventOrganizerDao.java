package ae.dao.impl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import ae.model.EventOrganizer;
import ae.dao.api.IEventOrganizerDao;

/**
 *
 * @author Aditya
 */
public class EventOrganizerDao extends UnicastRemoteObject implements IEventOrganizerDao {

    private Connection conn = null;
    private String strSql = "";

    public EventOrganizerDao(Connection conn) throws RemoteException {
        this.conn = conn;
    }

    @Override
    public String GenerateIDEO(EventOrganizer eo) throws RemoteException {
        String result = null;
        String x = "EOAMIKOM";
        System.out.println("Method Generate diakses secara remote");
        strSql = "SELECT * FROM EventOrganizer ORDER BY eo_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String id_eo = rs.getString("eo_id").substring(8);
                String AN = "" + (Integer.parseInt(id_eo) + 1);
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
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    

    @Override
    public int saveDataEo(EventOrganizer eo) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Event Organizer diakses secara remote");
        strSql = "INSERT INTO EventOrganizer (eo_id, eo_name, eo_name_pic, eo_inst, eo_alamat, eo_ponsel, eo_email, eo_website, eo_qrcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, eo.getEo_id());
            ps.setString(2, eo.getEo_nama());
            ps.setString(3, eo.getEo_nama_pic());
            ps.setString(4, eo.getEo_instansi());
            ps.setString(5, eo.getEo_alamat());
            ps.setString(6, eo.getEo_ponsel());
            ps.setString(7, eo.getEo_email());
            ps.setString(8, eo.getEo_website());
            ps.setBytes(9, eo.getEo_qr());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(EventOrganizer eo) throws RemoteException {
        int result = 0;
        System.out.println("Method update barang diakses secara remote");
        strSql = "UPDATE EventOrganizer SET eo_nama = ?, eo_inst = ?, eo_alamat = ?, eo_ponsel = ?, eo_email = ?, eo_website =? "
                + "WHERE eo_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, eo.getEo_nama());
            ps.setString(2, eo.getEo_instansi());
            ps.setString(3, eo.getEo_alamat());
            ps.setString(4, eo.getEo_ponsel());
            ps.setString(5, eo.getEo_email());
            ps.setString(6, eo.getEo_website());
            ps.setString(7, eo.getEo_id());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(EventOrganizer eo) throws RemoteException {
        int result = 0;
        System.out.println("Method delete Event Organizer diakses secara remote");
        strSql = "DELETE FROM EventOrganizer "
                + "WHERE eo_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, eo.getEo_id());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<EventOrganizer> getAll() throws RemoteException {
        // buat objek List (class Java Collection)
        List<EventOrganizer> daftarEO = new ArrayList<EventOrganizer>();
        System.out.println("Method getAll Event Organizer diakses secara remote");
        strSql = "SELECT eo_id, eo_name, eo_name_pic, eo_ponsel, eo_email, eo_website FROM EventOrganizer ORDER BY eo_id";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                EventOrganizer eo = new EventOrganizer();
                eo.setEo_id(rs.getString("eo_id"));
                eo.setEo_nama(rs.getString("eo_name"));
                eo.setEo_nama_pic(rs.getString("eo_name_pic"));
                eo.setEo_ponsel(rs.getString("eo_ponsel"));
                eo.setEo_email(rs.getString("eo_email"));
                eo.setEo_website(rs.getString("eo_website"));
                // simpan objek barang ke dalam objek class List
                daftarEO.add(eo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarEO;
    }

    @Override
    public List<EventOrganizer> getByName(String namaEo) throws RemoteException {
        // buat objek List (class Java Collection)
        List<EventOrganizer> daftarEO = new ArrayList<EventOrganizer>();
        System.out.println("Method getByName Event Organizer diakses secara remote");
        strSql = "SELECT eo_id, eo_name, eo_name_pic, eo_ponsel, eo_email, eo_website FROM EventOrganizer WHERE eo_name LIKE ? ORDER BY eo_id";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, "%" + namaEo + "%");
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                EventOrganizer eo = new EventOrganizer();
                eo.setEo_id(rs.getString("eo_id"));
                eo.setEo_nama(rs.getString("eo_name"));
                eo.setEo_nama_pic(rs.getString("eo_name_pic"));
                eo.setEo_ponsel(rs.getString("eo_ponsel"));
                eo.setEo_email(rs.getString("eo_email"));
                eo.setEo_website(rs.getString("eo_website"));
                // simpan objek barang ke dalam objek class List
                daftarEO.add(eo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarEO;
    }
}
