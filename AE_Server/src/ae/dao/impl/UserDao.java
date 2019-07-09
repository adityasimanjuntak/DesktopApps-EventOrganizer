/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.dao.impl;

import ae.dao.api.IUserDao;
import ae.model.Login;
import ae.model.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aditya
 */
public class UserDao extends UnicastRemoteObject implements IUserDao {

    private Connection conn = null;
    private String strSql = "";

    public UserDao(Connection conn) throws RemoteException {
        this.conn = conn;
    }

    @Override
    public int saveDataEmp(User U) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Karyawan diakses secara remote");
        strSql = "INSERT INTO Employee (emp_id, emp_name, emp_gender, emp_noponsel, emp_email, emp_foto, emp_qrcode) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, U.getEmp_id());
            ps.setString(2, U.getEmp_name());
            ps.setString(3, U.getEmp_gender());
            ps.setString(4, U.getEmp_noponsel());
            ps.setString(5, U.getEmp_email());
            ps.setBytes(6, U.getEmp_foto());
            ps.setBytes(7, U.getEmp_qrcode());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int saveDataEmpLogin(Login L) throws RemoteException {
        int result = 0;
        System.out.println("Method Save Data Karyawan diakses secara remote");
        strSql = "INSERT INTO User_Login (u_id, u_pwd) VALUES (?, ?)";
        try {
            // buat objek PreparedStatement
            PreparedStatement ps = conn.prepareStatement(strSql);
            // set nilai parameter yg akan dikirim ke sql
            ps.setString(1, L.getUsername());
            ps.setString(2, L.getPassword());
            // jalankan perintah sql
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventOrganizerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
