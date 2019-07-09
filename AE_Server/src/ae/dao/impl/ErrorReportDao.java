/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.impl;

import ae.dao.api.IErrorReportDao;
import ae.model.ErrorReport;
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
public class ErrorReportDao extends UnicastRemoteObject implements IErrorReportDao {

    private Connection conn = null;
    private String strSql = "";

    public ErrorReportDao(Connection conn) throws RemoteException {
        this.conn = conn;
    }

    @Override
    public String GenerateIDError(ErrorReport er) throws RemoteException {
        String result = null;
        String x = "ERRORAPP";
        System.out.println("Method Generate ID Error diakses secara remote");
        strSql = "SELECT * FROM app_error_report ORDER BY error_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String id_eo = rs.getString("error_id").substring(8);
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
    public int saveDataError(ErrorReport er) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Error Report diakses secara remote");
        strSql = "INSERT INTO App_Error_Report (error_id, error_id_employee, error_title, error_text, error_pic, error_status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, er.getError_id());
            ps.setString(2, er.getError_emp_id());
            ps.setString(3, er.getError_title());
            ps.setString(4, er.getError_text());
            ps.setString(5, "Nothing");
            ps.setString(6, er.getError_state());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<ErrorReport> getAll() throws RemoteException {
        // buat objek List (class Java Collection)
        List<ErrorReport> daftarError = new ArrayList<ErrorReport>();
        System.out.println("Method getAll Error Report diakses secara remote");
        strSql = "SELECT error_id, error_id_employee, error_title, error_text, error_pic, error_status from App_error_report";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // buat objek ResultSet utk menampung hasil SELECT
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // mapping objek ResultSet ke object barang
                ErrorReport ER = new ErrorReport();
                ER.setError_id(rs.getString("error_id"));
                ER.setError_emp_id(rs.getString("error_id_employee"));
                ER.setError_title(rs.getString("error_title"));
                ER.setError_text(rs.getString("error_text"));
                ER.setError_pic(rs.getString("error_pic"));
                ER.setError_state(rs.getString("error_status"));
                // simpan objek barang ke dalam objek class List
                daftarError.add(ER);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ErrorReportDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarError;
    }

    @Override
    public int updateError(ErrorReport ER) throws RemoteException {
        int result = 0;
        System.out.println("Method update Error diakses secara remote");
        strSql = "UPDATE App_Error_Report SET error_id_employee = ?, error_title = ?, error_text = ?, error_pic = ?, error_status = ? WHERE error_id = ?";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, ER.getError_emp_id());
            ps.setString(2, ER.getError_title());
            ps.setString(3, ER.getError_text());
            ps.setString(4, ER.getError_pic());
            ps.setString(5, ER.getError_state());
            ps.setString(6, ER.getError_id());            
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ErrorReportDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
